package br.com.bomrastreio.api.object.event;

import br.com.bomrastreio.api.correios.trace.wsdl.TraceEvent;
import br.com.bomrastreio.api.notification.RetryableMailSender;
import br.com.bomrastreio.api.object.ObjectStatus;
import br.com.bomrastreio.api.object.TrackingObject;
import br.com.bomrastreio.api.setting.SettingKey;
import br.com.bomrastreio.api.setting.SettingService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ObjectStatusChangedMailListener extends ObjectStatusChangedFremarkerListener {

  private static final String PREFIX = "/object/mail";
  private static final Map<ObjectStatus, String> SUBJECT_MAPPING = new HashMap<>();

  static {
    SUBJECT_MAPPING.put(ObjectStatus.ACTION_REQUIRED, "Ação requerida para o objeto %s");
    SUBJECT_MAPPING.put(ObjectStatus.DELAYED, "Objeto %s atrasado");
    SUBJECT_MAPPING.put(ObjectStatus.DELIVERED, "Objeto %s entregue");
    SUBJECT_MAPPING.put(ObjectStatus.IN_TRANSIT, "Objeto %s a caminho");
  }

  private final Environment environment;
  private final SettingService settingService;
  private final RetryableMailSender retryableMailSender;

  @Autowired
  public ObjectStatusChangedMailListener(
    Environment environment, Configuration configuration, SettingService settingService, RetryableMailSender retryableMailSender
  ) {
    super(configuration);
    this.environment = environment;
    this.settingService = settingService;
    this.retryableMailSender = retryableMailSender;
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

    if (settingService.isActive(SettingKey.MAIL_NOTIFICATION) && object.canNotifyByMail()) {
      retryableMailSender.send(createPreparator(object, event.getTraceEvent()));
    }
  }

  /**
   * Create a preparator for the mime message basead on the template.
   *
   * @param object     Changed object
   * @param traceEvent Last event from correios
   * @return A new {@link MimeMessagePreparator}
   */
  private MimeMessagePreparator createPreparator(TrackingObject object, TraceEvent traceEvent) {
    return mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
      messageHelper.setTo(object.getTenant());
      messageHelper.setFrom(
        environment.getRequiredProperty("app.mail.default-sender"),
        environment.getRequiredProperty("app.mail.default-personal"));

      String subject = SUBJECT_MAPPING.get(object.getStatus());
      messageHelper.setSubject(String.format(subject, object.getCode()));

      Map<String, Object> model = new HashMap<String, Object>() {{
        put("code", object.getCode());
        put("date", traceEvent.getDate());
        put("time", traceEvent.getTime());
      }};

      messageHelper.setText(createContent(PREFIX, object.getStatus(), model), true);
    };
  }
}
