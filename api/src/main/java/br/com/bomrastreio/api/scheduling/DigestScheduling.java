package br.com.bomrastreio.api.scheduling;

import br.com.bomrastreio.api.digest.DigestDeliverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DigestScheduling {

  private final DigestDeliverer digestDeliverer;

  @Autowired
  public DigestScheduling(DigestDeliverer digestDeliverer) {
    this.digestDeliverer = digestDeliverer;
  }

  /**
   * When it runs, it delegates the digest delivery
   * to {@link DigestDeliverer} class.
   */
  @Scheduled(cron = "0 0 20 * * ?")
  public void run() {
    digestDeliverer.deliver();
  }

}
