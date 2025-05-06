package no.ntnu.gr10.bachelor_producer_rest_api.exception;

import jakarta.persistence.EntityNotFoundException;

public class FisheryActivityNotFoundException extends EntityNotFoundException {
  public FisheryActivityNotFoundException(String message) {
    super(message);
  }
}