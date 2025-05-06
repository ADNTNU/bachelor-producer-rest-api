package no.ntnu.gr10.bachelor_producer_rest_api.administrator;

import no.ntnu.gr10.bachelor_producer_rest_api.exception.InvalidRoleException;

public enum AdministratorRole {
  OWNER,
  ADMINISTRATOR;

  public static AdministratorRole fromString(String value) throws InvalidRoleException {
    try {
      return AdministratorRole.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException ex) {
      throw new InvalidRoleException("Invalid role: " + value);
    }
  }
}
