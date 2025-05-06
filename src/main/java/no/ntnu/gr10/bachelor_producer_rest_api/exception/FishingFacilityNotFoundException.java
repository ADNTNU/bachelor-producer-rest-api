package no.ntnu.gr10.bachelor_producer_rest_api.exception;

import jakarta.persistence.EntityNotFoundException;

public class FishingFacilityNotFoundException extends EntityNotFoundException {
  public FishingFacilityNotFoundException(String message) {
    super(message);
  }
}
