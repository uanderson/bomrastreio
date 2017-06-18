package br.com.bomrastreio.api.object;

import br.com.bomrastreio.api.support.ResponseEntitySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/objects", produces = "application/json")
public class ObjectApi {

  private final ObjectService objectService;
  private final ObjectMapper objectMapper;

  @Autowired
  public ObjectApi(ObjectService objectService, ObjectMapper objectMapper) {
    this.objectService = objectService;
    this.objectMapper = objectMapper;
  }

  @PostMapping
  public ResponseEntity<ObjectResource> post(@RequestBody ObjectResource resource) {
    TrackingObject savedObject = objectService.saveOne(resource);
    ObjectResource savedResource = objectMapper.map(savedObject);
    return ResponseEntitySupport.created(savedObject.getCode(), savedResource);
  }

  @PostMapping("/batches")
  public ResponseEntity<List<ObjectResource>> post(@RequestBody List<ObjectResource> resources) {
    List<TrackingObject> savedObjects = objectService.saveAll(resources);
    List<ObjectResource> savedResources = savedObjects.stream()
      .map(objectMapper::map)
      .collect(Collectors.toList());

    return ResponseEntity.ok(savedResources);
  }

  @PutMapping("/{code}")
  public ResponseEntity<ObjectResource> put(
    @PathVariable String code, @RequestBody ObjectResource resource
  ) {
    TrackingObject savedObject = objectService.update(code, resource);
    ObjectResource savedResource = objectMapper.map(savedObject);
    return ResponseEntity.ok(savedResource);
  }

  @DeleteMapping("/{code}")
  public ResponseEntity<?> delete(@PathVariable String code) {
    objectService.delete(code);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<ObjectResource>> get(Pageable pageable) {
    Page<TrackingObject> objects = objectService.findAll(pageable);
    Page<ObjectResource> resources = objects.map(objectMapper::map);
    return ResponseEntitySupport.paged(resources);
  }

  @GetMapping("/{code}")
  public ResponseEntity<ObjectResource> get(@PathVariable String code) {
    TrackingObject object = objectService.findOne(code);
    return ResponseEntity.ok(objectMapper.map(object));
  }

}
