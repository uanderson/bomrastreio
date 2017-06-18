package br.com.bomrastreio.api.security;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Support for Keycloak integration.
 */
public final class KeycloakHolder {

  /**
   * Get the username from the token.
   *
   * @return Preferred username
   */
  public static String getUsername() {
    return getToken().getPreferredUsername();
  }

  /**
   * Get the token from the Keycloak context.
   *
   * @return Token object
   */
  private static AccessToken getToken() {
    return getSecurityContext().getToken();
  }

  /**
   * Get the Keycloak principal from request
   *
   * @return KeycloakSecurityContext
   */
  private static KeycloakSecurityContext getSecurityContext() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
      .currentRequestAttributes()).getRequest();

    return ((KeycloakPrincipal<?>) request.getUserPrincipal()).getKeycloakSecurityContext();
  }

}
