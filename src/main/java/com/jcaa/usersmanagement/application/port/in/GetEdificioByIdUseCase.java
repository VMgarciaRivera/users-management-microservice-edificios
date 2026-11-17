package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.Edificio;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface GetEdificioByIdUseCase {
  Edificio execute(@NotNull UUID id);
}
