package br.com.bomrastreio.api.tenant;

import br.com.bomrastreio.api.security.KeycloakHolder;
import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Hold the current tenant ID. Always try recover from Keycloak first.
 */
public final class TenantHolder {

  private static final ThreadLocal<String> TENANT = new InheritableThreadLocal<>();

  /**
   * Get the current tenant, per thread.
   *
   * @return Current tenant id
   */
  public static String get() {
    if (StringUtils.isNoneBlank(TENANT.get())) {
      return TENANT.get();
    }

    try {
      return KeycloakHolder.getUsername();
    } catch (Exception ex) {
      return "";
    }
  }

  /**
   * Set the current tenant id, per thread.
   *
   * @param tenant TenantHolder id
   */
  public static void set(String tenant) {
    TENANT.set(checkNotNull(tenant));
  }

  /**
   * Remove the current tenant from current thread.
   */
  public static void remove() {
    TENANT.remove();
  }

}
