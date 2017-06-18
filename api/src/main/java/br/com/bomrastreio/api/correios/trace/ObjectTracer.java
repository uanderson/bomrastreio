package br.com.bomrastreio.api.correios.trace;

import br.com.bomrastreio.api.correios.trace.wsdl.TraceObject;
import br.com.bomrastreio.api.object.ObjectService;
import br.com.bomrastreio.api.object.ObjectUpdater;
import br.com.bomrastreio.api.object.TrackingObject;
import br.com.bomrastreio.api.tenant.TenantHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Trace objects from correios.
 */
@Service
public class ObjectTracer {

  private static final Logger LOGGER = LoggerFactory.getLogger(ObjectTracer.class);

  private final TraceClient traceClient;
  private final ObjectService objectService;
  private final ObjectUpdater objectUpdater;

  @Autowired
  public ObjectTracer(
    TraceClient traceClient, ObjectService objectService, ObjectUpdater objectUpdater) {
    this.traceClient = traceClient;
    this.objectService = objectService;
    this.objectUpdater = objectUpdater;
  }

  /**
   * Find tenants and start tracing the objects per tenant.
   */
  public void trace() {
    objectService.findTraceableTenants()
      .forEach(this::traceBy);
  }

  /**
   * Trace objects per tenant.
   *
   * @param tenantId TenantHolder id
   */
  private void traceBy(String tenantId) {
    try {
      TenantHolder.set(tenantId);
      int pageNumber = 0;

      for (;;) {
        PageRequest pageRequest = new PageRequest(pageNumber, 500);
        Page<TrackingObject> objectPage = objectService.findTraceableObjects(pageRequest);

        if (!objectPage.hasContent()) {
          break;
        }

        Page<String> codePage = objectPage.map(TrackingObject::getCode);
        List<TraceObject> traceObjects = traceClient.traceObjects(codePage.getContent());
        traceObjects.sort(Comparator.comparing(TraceObject::getNumber));

        process(objectPage.getContent(), traceObjects);
        pageNumber++;
      }
    } finally {
      TenantHolder.remove();
    }
  }

  /**
   * Call correios service and now update information about objects.
   *
   * @param objects      {@link TrackingObject} list
   * @param traceObjects {@link TraceObject} list
   */
  private void process(List<TrackingObject> objects, List<TraceObject> traceObjects) {
    TraceObject searchObject = new TraceObject();
    List<TrackingObject> updatedObjects = new ArrayList<>();
    Comparator<TraceObject> numberComparator = Comparator.comparing(TraceObject::getNumber);

    objects.forEach(object -> {
      searchObject.setNumber(object.getCode());
      int index = Collections.binarySearch(traceObjects, searchObject, numberComparator);
      TraceObject traceObject = traceObjects.get(index);

      if (objectUpdater.update(object, traceObject)) {
        updatedObjects.add(object);
      }
    });

    updatedObjects.forEach(object -> {
      try {
        objectService.save(object);
      } catch (Exception ex) {
        LOGGER.error("Error trying to save object " + object.getCode(), ex);
      }
    });
  }
}
