package br.com.bomrastreio.api.audit;

import br.com.bomrastreio.api.tenant.TenantHolder;
import org.springframework.data.domain.AuditorAware;

public class SecurityAuditorAware implements AuditorAware<String> {

  @Override
  public String getCurrentAuditor() {
    return TenantHolder.get();
  }

}
