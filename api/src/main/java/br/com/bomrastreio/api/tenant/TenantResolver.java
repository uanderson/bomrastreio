package br.com.bomrastreio.api.tenant;

/**
 * Resolves tenant id for multi tenant application.
 */
public class TenantResolver {

  /**
   * Get the tenant id from thread or from Keycloak.
   *
   * @return TenantHolder id
   */
  public String getTenant() {
    return TenantHolder.get();
  }

}
