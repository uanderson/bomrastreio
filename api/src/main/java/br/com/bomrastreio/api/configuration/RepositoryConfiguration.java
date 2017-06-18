package br.com.bomrastreio.api.configuration;

import br.com.bomrastreio.api.tenant.TenantResolver;
import br.com.bomrastreio.api.Application;
import br.com.bomrastreio.api.tenant.TenantTransactionManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
  basePackageClasses = Application.class,
  enableDefaultTransactions = false
)
@EnableTransactionManagement
public class RepositoryConfiguration extends JpaBaseConfiguration {

  @Autowired
  protected RepositoryConfiguration(
    DataSource dataSource, JpaProperties properties,
    ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider,
    ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers
  ) {
    super(dataSource, properties, jtaTransactionManagerProvider, transactionManagerCustomizers);
  }

  @Override
  protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
    return new EclipseLinkJpaVendorAdapter();
  }

  @Override
  protected Map<String, Object> getVendorProperties() {
    return new HashMap<String, Object>() {{
      put("eclipselink.weaving", "false");
      put("eclipselink.jdbc.allow-native-sql-queries", "true");
    }};
  }

  @Bean
  public TenantResolver tenantResolver() {
    return new TenantResolver();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new TenantTransactionManager(tenantResolver());
  }

}
