package br.com.bomrastreio.api.object;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ObjectSaver {

  private final ObjectRepository objectRepository;

  public ObjectSaver(ObjectRepository objectRepository) {
    this.objectRepository = objectRepository;
  }

  @Transactional
  public TrackingObject save(TrackingObject trackingObject) {
    return objectRepository.save(trackingObject);
  }

  @Transactional
  public List<TrackingObject> save(List<TrackingObject> objects) {
    return objectRepository.save(objects);
  }

}
