package br.com.bomrastreio.api.configuration;

import br.com.bomrastreio.api.Application;
import br.com.bomrastreio.api.audit.SecurityAuditorAware;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackageClasses = {
  Application.class, Jsr310JpaConverters.class
})
public class DatabaseConfiguration {

  @Bean
  public AuditorAware<String> auditorProvider() {
    return new SecurityAuditorAware();
  }
}
