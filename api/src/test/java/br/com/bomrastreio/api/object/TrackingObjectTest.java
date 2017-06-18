package br.com.bomrastreio.api.object;

import br.com.bomrastreio.api.correios.trace.TraceStatus;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TrackingObjectTest {

  @Test
  public void should_update_to_delayed_if_is_delayed() {
    TrackingObject trackingObject = createTrackingObject(Instant.now().minus(1, ChronoUnit.DAYS));
    assertThat(trackingObject.updateStatus(TraceStatus.BDE_45), is(true));
    assertThat(trackingObject.getStatus(), is(Matchers.equalTo(ObjectStatus.DELAYED)));
  }

  @Test
  public void should_test_if_is_delayed() {
    TrackingObject trackingObject = createTrackingObject(Instant.now().minus(1, ChronoUnit.DAYS));

    assertThat(trackingObject.isDelayed(TraceStatus.UND_00), is(true));
    assertThat(trackingObject.isDelayed(TraceStatus.BDE_02), is(false));
    assertThat(trackingObject.isDelayed(TraceStatus.BDE_56), is(false));
    assertThat(trackingObject.isDelayed(TraceStatus.BDI_00), is(false));
    assertThat(trackingObject.isDelayed(TraceStatus.BDE_07), is(true));
  }

  @Test
  public void should_not_be_delayed_if_has_restriction() {
    TrackingObject trackingObject = createTrackingObject(Instant.now().minus(1, ChronoUnit.DAYS));

    assertThat(trackingObject.isDelayed(TraceStatus.BDE_66), is(false));
    assertThat(trackingObject.isDelayed(TraceStatus.BDI_66), is(false));
    assertThat(trackingObject.isDelayed(TraceStatus.BDR_66), is(false));
  }

  private TrackingObject createTrackingObject(Instant instant) {
    return TrackingObject.builder()
        .withId(UUID.randomUUID().toString())
        .withCode("RE462074738SE")
        .withAddressee("Agar Kirstin")
        .withEta(instant)
        .build();
  }

}
