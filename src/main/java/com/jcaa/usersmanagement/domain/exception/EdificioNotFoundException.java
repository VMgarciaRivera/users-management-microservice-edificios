package com.jcaa.usersmanagement.domain.exception;

import java.util.UUID;

public class EdificioNotFoundException extends DomainException {

  public EdificioNotFoundException(final String message) {
    super(message);
  }

  public static EdificioNotFoundException becauseIdWasNotFound(final UUID id) {
    return new EdificioNotFoundException("No se encontró el edificio con ID: " + id);
  }
}
