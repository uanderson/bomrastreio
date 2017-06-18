package br.com.bomrastreio.api.object.event;

import br.com.bomrastreio.api.correios.trace.wsdl.TraceEvent;
import br.com.bomrastreio.api.notification.Notification;
import br.com.bomrastreio.api.notification.NotificationType;
import br.com.bomrastreio.api.object.TrackingObject;
import br.com.bomrastreio.api.setting.SettingKey;
import br.com.bomrastreio.api.setting.SettingService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ObjectStatusChangedWebSocketListener extends ObjectStatusChangedFremarkerListener {

  private static final String PREFIX = "/object/notification";

  private final SettingService settingService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @Autowired
  public ObjectStatusChangedWebSocketListener(
    Configuration configuration, SettingService settingService, SimpMessagingTemplate simpMessagingTemplate
  ) {
    super(configuration);
    this.settingService = settingService;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  /**
   * Listen to the event and notify if needed.
   *
   * @param event Status changed event
   */
  @Async
  @EventListener
  public void listen(ObjectStatusChangedEvent event) {
    TrackingObject object = event.getObject();

    if (settingService.isActive(SettingKey.WEB_SOCKET_NOTIFICATION) && object.canNotifyByWebSocket()) {
      Notification notification = createNotification(object, event.getTraceEvent());
      simpMessagingTemplate.convertAndSendToUser(object.getTenant(), "/notifications", notification);
    }
  }

  /**
   * Creates event to send for the user.
   *
   * @param object     Tracking object
   * @param traceEvent Correios trace event
   * @return New {@link Notification}
   */
  private Notification createNotification(TrackingObject object, TraceEvent traceEvent) {
    Map<String, Object> model = new HashMap<String, Object>() {{
      put("code", object.getCode());
      put("date", traceEvent.getDate());
      put("time", traceEvent.getTime());
    }};

    String content = createContent(PREFIX, object.getStatus(), model);
    return new Notification(NotificationType.OBJECT, content);
  }
}
