package br.com.bomrastreio.api.object;

import br.com.bomrastreio.api.correios.trace.TraceStatus;
import br.com.bomrastreio.api.correios.trace.wsdl.TraceEvent;
import br.com.bomrastreio.api.correios.trace.wsdl.TraceObject;
import br.com.bomrastreio.api.object.event.ObjectStatusChangedEvent;
import br.com.bomrastreio.api.setting.SettingKey;
import br.com.bomrastreio.api.setting.SettingService;
import br.com.bomrastreio.api.tenant.TenantHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

/**
 * Responsible for update the tracking object event.
 */
@Service
public class ObjectUpdater {

  private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUpdater.class);
  private static final String EVENT_DATETIME_PATTERN = "dd/MM/yyyyHH:mm";

  private final ObjectMapper objectMapper;
  private final SettingService settingService;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  public ObjectUpdater(
    ObjectMapper objectMapper, SettingService settingService, ApplicationEventPublisher applicationEventPublisher
  ) {
    this.objectMapper = objectMapper;
    this.settingService = settingService;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  /**
   * Update the tracking object status based on the correios status.
   *
   * @param object      Tracking object
   * @param traceObject Correios trace object
   * @return true if was updated, false otherwise
   */
  public boolean update(TrackingObject object, TraceObject traceObject) {
    if (StringUtils.isNoneBlank(traceObject.getError())) {
      LOGGER.warn("Object {} returned an error: {}",
        traceObject.getNumber(), traceObject.getError());
      return false;
    }

    TraceEvent lastEvent = findLastEvent(traceObject);
    TraceStatus traceStatus = TraceStatus.of(lastEvent.getType(), lastEvent.getStatus());
    boolean updated = object.updateStatus(traceStatus);

    if (updated) {
      attachJson(object, traceObject);
      tryNotify(object, lastEvent);
    }

    return updated;
  }

  /**
   * Attach JSON to the object
   *
   * @param object      Target object
   * @param traceObject Current trace object
   */
  private void attachJson(TrackingObject object, TraceObject traceObject) {
    try {
      object.attachJson(objectMapper.writeValueAsString(traceObject));
    } catch (JsonProcessingException ex) {
      LOGGER.error("Wasn't possible attach JSON to the object", ex);
    }
  }

  /**
   * Notify if the notification setting is enabled
   *
   * @param object    Target object
   * @param lastEvent Last trace event from correios
   */
  private void tryNotify(TrackingObject object, TraceEvent lastEvent) {
    if (settingService.isActive(SettingKey.NOTIFICATION)) {
      LOGGER.debug("Notifying status update for object {}", object.getCode());

      ObjectStatusChangedEvent event = ObjectStatusChangedEvent.builder()
        .withObject(object)
        .withTraceEvent(lastEvent)
        .withTenant(TenantHolder.get())
        .build();

      applicationEventPublisher.publishEvent(event);
    }
  }

  /**
   * Sort the event list in reverse to ensure the last event is the
   * correct one in order to update the {@link TrackingObject}.
   *
   * @param traceObject Correios trace object
   * @return Most recent event
   */
  private TraceEvent findLastEvent(TraceObject traceObject) {
    Comparator<TraceEvent> comparator = (objectOne, objectTwo) -> {
      String valueOne = objectOne.getDate() + objectOne.getTime();
      String valueTwo = objectTwo.getDate() + objectTwo.getTime();

      LocalDateTime dateOne = LocalDateTime.parse(valueOne, DateTimeFormatter.ofPattern(EVENT_DATETIME_PATTERN));
      LocalDateTime dateTwo = LocalDateTime.parse(valueTwo, DateTimeFormatter.ofPattern(EVENT_DATETIME_PATTERN));

      return dateOne.compareTo(dateTwo);
    };

    List<TraceEvent> events = traceObject.getEvents();
    events.sort(comparator.reversed());

    return traceObject.getEvents().get(0);
  }
}
