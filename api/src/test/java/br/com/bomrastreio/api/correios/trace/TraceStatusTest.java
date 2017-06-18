package br.com.bomrastreio.api.correios.trace;

import br.com.bomrastreio.api.object.ObjectStatus;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TraceStatusTest {

  @Test
  public void should_be_returned_correctly_when_exists() {
    TraceStatus status = TraceStatus.of("TRI", "00");
    assertThat(status.getObjectStatus(), is(equalTo(ObjectStatus.IN_TRANSIT)));
  }

  @Test
  public void should_status_be_undefined_when_not_found() {
    TraceStatus status = TraceStatus.of("ANY", "01");
    assertThat(status.getObjectStatus(), is(equalTo(ObjectStatus.UNDEFINED)));
  }

}
