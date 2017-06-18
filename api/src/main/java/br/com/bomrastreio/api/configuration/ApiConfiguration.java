package br.com.bomrastreio.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.Filter;
import java.util.Locale;

@Configuration
public class ApiConfiguration {

  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setDefaultLocale(new Locale("pt", "BR"));

    return localeResolver;
  }

  @Bean
  public Filter shallowETagHeaderFilter() {
    return new ShallowEtagHeaderFilter();
  }
}
