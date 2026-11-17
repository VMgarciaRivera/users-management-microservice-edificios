package com.jcaa.usersmanagement.application.port.in;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface DeleteEdificioUseCase {
  void execute(@NotNull UUID id);
}
