package no.ntnu.gr10.bachelor_producer_rest_api.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Application scopes as an enum, implementing Spring Security’s GrantedAuthority.
 */
public enum Scope implements GrantedAuthority {
  FISHERY_ACTIVITY("fishery_activity"),
  ADMIN("admin");

  private final String authority;

  Scope(String authority) {
    this.authority = authority;
  }

  /**
   * Returns the authority string as it appears in the JWT claim.
   *
   * @return the authority key in the token
   */
  public String getAuthority() {
    return authority;
  }
}