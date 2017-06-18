package br.com.bomrastreio.api.scheduling;

import br.com.bomrastreio.api.correios.trace.ObjectTracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Scheduled task used to track correios objects.
 */
@Service
public class TrackScheduling {

  private final ObjectTracer objectTracer;

  @Autowired
  public TrackScheduling(ObjectTracer objectTracer) {
    this.objectTracer = objectTracer;
  }

  /**
   * When it runs, it delegates the trace responsibility
   * to the {@link ObjectTracer} class.
   */
  @Scheduled(cron = "0 0/30 * * * ?")
  public void run() {
    objectTracer.trace();
  }
}
