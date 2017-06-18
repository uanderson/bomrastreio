package br.com.bomrastreio.api.correios.trace;

import br.com.bomrastreio.api.correios.trace.wsdl.TraceObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TraceClientTest {

  @Autowired
  private TraceClient traceClient;

  @Test
  public void should_get_simple_trace_object_from_correios() {
    List<TraceObject> traceObjects = traceClient
      .traceObjects(Collections.singletonList("RE462074738SE"));

    assertFalse(traceObjects.isEmpty());

    TraceObject traceObject = traceObjects.get(0);
    assertThat(traceObject.getNumber(), is(equalTo("RE462074738SE")));
    assertThat(traceObject.getAcronym(), is(equalTo("RE")));
    assertThat(traceObject.getName(), is(equalTo("MALA DIRETA POSTAL ESPECIAL")));
    assertThat(traceObject.getEvents(), hasSize(5));
  }

  @Test
  public void should_return_empty_when_passed_list_is_empty() {
    List<TraceObject> traceObjects = traceClient.traceObjects(Collections.emptyList());
    assertThat(traceObjects, is(empty()));
  }
}
