package br.com.bomrastreio.api.correios.trace;

import br.com.bomrastreio.api.correios.trace.wsdl.TraceObject;
import br.com.bomrastreio.api.correios.trace.wsdl.TraceResponse;
import br.com.bomrastreio.api.correios.trace.wsdl.TraceWebService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Executes webservice to now objects information.
 */
@Component
public class TraceClient {

  private final Environment environment;
  private final TraceWebService traceWebService;

  @Autowired
  public TraceClient(Environment environment, TraceWebService traceWebService) {
    this.environment = environment;
    this.traceWebService = traceWebService;
  }

  /**
   * Trace all objects in the codes list.
   *
   * @param codes Objects codes
   * @return Updated objects list
   */
  public List<TraceObject> traceObjects(List<String> codes) {
    if (codes.isEmpty()) {
      return Collections.emptyList();
    }

    String user = environment.getRequiredProperty("app.correios.user");
    String password = environment.getRequiredProperty("app.correios.password");
    String searchType = environment.getRequiredProperty("app.correios.search-type");
    String resultType = environment.getRequiredProperty("app.correios.result-type");
    String language = environment.getRequiredProperty("app.correios.language");

    TraceResponse traceResponse = traceWebService.trace(
      user, password, searchType, resultType, language, codes);

    return traceResponse.getObjects();
  }

}
