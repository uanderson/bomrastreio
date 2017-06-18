package br.com.bomrastreio.api.object;

import br.com.bomrastreio.api.exception.BadRequestException;
import br.com.bomrastreio.api.exception.NotFoundException;
import br.com.bomrastreio.api.exception.UniqueObjectException;
import br.com.bomrastreio.api.exception.propagation.ExceptionPropagator;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.bomrastreio.api.object.ObjectSpecifications.isTraceable;

@Service
@Validated
public class ObjectService {

  private final ExceptionPropagator exceptionPropagator;
  private final ObjectRepository objectRepository;
  private final ObjectSaver objectSaver;

  @Autowired
  public ObjectService(
    ExceptionPropagator exceptionPropagator, ObjectRepository objectRepository, ObjectSaver objectSaver
  ) {
    this.exceptionPropagator = exceptionPropagator;
    this.objectRepository = objectRepository;
    this.objectSaver = objectSaver;
  }

  /**
   * Save a resource to the database after validation.
   *
   * @param resource Resource to be saved
   * @return The saved object
   */
  public TrackingObject saveOne(@Valid ObjectResource resource) {
    return save(createTrackingObject(resource));
  }

  /**
   * Save multiple resources to the database after validation.
   *
   * @param resources Resources to be saved
   * @return The saved objects
   * @throws UniqueObjectException
   * @throws RuntimeException
   */
  public List<TrackingObject> saveAll(@Valid List<ObjectResource> resources) {
    if (resources.isEmpty()) {
      throw new BadRequestException("object.batch.minimum_required");
    }

    List<TrackingObject> objects = resources.stream()
      .map(this::createTrackingObject)
      .collect(Collectors.toList());

    return save(objects);
  }

  public TrackingObject save(TrackingObject object) {
    try {
      return objectSaver.save(object);
    } catch (TransactionSystemException ex) {
      throw exceptionPropagator.propagate()
        .withThrowable(Throwables.getRootCause(ex))
        .withCodePrefix("object")
        .withArguments(object.getCode())
        .now();
    }
  }

  public List<TrackingObject> save(List<TrackingObject> objects) {
    try {
      return objectSaver.save(objects);
    } catch (TransactionSystemException ex) {
      throw exceptionPropagator.propagate()
        .withThrowable(Throwables.getRootCause(ex))
        .withCodePrefix("object.batch")
        .now();
    }
  }

  @Transactional
  public TrackingObject update(String code, @Valid ObjectResource resource) {
    TrackingObject object = findOne(code);
    object.updateEta(resource.getEta());
    object.updateAddressee(resource.getAddressee());
    object.updateNote(resource.getNote());

    return objectRepository.save(object);
  }

  @Transactional
  public void delete(String code) {
    Preconditions.checkNotNull(code, "ID cannot be null");
    objectRepository.delete(findOne(code));
  }

  @Transactional(readOnly = true)
  public Page<TrackingObject> findAll(Pageable pageable) {
    Pageable defaultPageable;

    if (Objects.isNull(pageable.getSort())) {
      Sort sort = new Sort(Sort.Direction.DESC,
        TrackingObject_.createdAt.getName(), TrackingObject_.addressee.getName());

      defaultPageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
    } else {
      defaultPageable = pageable;
    }

    return objectRepository.findAll(defaultPageable);
  }

  @Transactional(readOnly = true)
  public TrackingObject findOne(String code) {
    return objectRepository.findByCode(code)
      .orElseThrow(() -> new NotFoundException("object.not_found", code));
  }

  @Transactional(readOnly = true)
  public List<?> findByCodes(Set<String> references) {
    return objectRepository.findByCodeIn(references);
  }

  @Transactional(readOnly = true)
  public Set<String> findTraceableTenants() {
    return objectRepository.findTraceableTenants();
  }

  @Transactional(readOnly = true)
  public Page<TrackingObject> findTraceableObjects(Pageable pageable) {
    return objectRepository.findAll(isTraceable(), pageable);
  }

  private TrackingObject createTrackingObject(ObjectResource resource) {
    return TrackingObject.builder()
      .withRandomId()
      .withCode(resource.getCode())
      .withEta(resource.getEta())
      .withAddressee(resource.getAddressee())
      .withNote(resource.getNote())
      .build();
  }
}
