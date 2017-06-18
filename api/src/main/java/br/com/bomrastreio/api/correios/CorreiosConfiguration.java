package br.com.bomrastreio.api.correios;

import br.com.bomrastreio.api.correios.trace.wsdl.TraceWebService;
import br.com.bomrastreio.api.correios.trace.wsdl.TraceWebServiceWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorreiosConfiguration {

  @Bean
  public TraceWebService traceWebService() {
    return new TraceWebServiceWrapper().getServicePort();
  }
}
