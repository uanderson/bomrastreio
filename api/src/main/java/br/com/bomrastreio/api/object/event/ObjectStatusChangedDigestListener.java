package br.com.bomrastreio.api.object.event;

import br.com.bomrastreio.api.digest.Digest;
import br.com.bomrastreio.api.digest.DigestService;
import br.com.bomrastreio.api.digest.DigestType;
import br.com.bomrastreio.api.object.TrackingObject;
import br.com.bomrastreio.api.setting.SettingKey;
import br.com.bomrastreio.api.setting.SettingService;
import br.com.bomrastreio.api.tenant.TenantHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ObjectStatusChangedDigestListener {

  private final SettingService settingService;
  private final DigestService digestService;

  @Autowired
  public ObjectStatusChangedDigestListener(
    SettingService settingService, DigestService digestService
  ) {
    this.settingService = settingService;
    this.digestService = digestService;
  }

  /**
   * Collect the digest if the setting is active.
   *
   * @param event Status changed event
   */
  @Async
  @EventListener
  public void listen(ObjectStatusChangedEvent event) {
    try {
      TenantHolder.set(event.getTenant());

      if (settingService.isActive(SettingKey.DIGEST_MAIL_NOTIFICATION)) {
        collectEvent(event);
      }
    } finally {
      TenantHolder.remove();
    }
  }

  /**
   * Use the {@link DigestService} to collect the event.
   *
   * @param event Changed status event
   */
  private void collectEvent(ObjectStatusChangedEvent event) {
    TrackingObject object = event.getObject();
    digestService.collect(Digest.builder()
      .withType(DigestType.OBJECT)
      .withReference(object.getCode())
      .withRandomId()
      .build());
  }
}
