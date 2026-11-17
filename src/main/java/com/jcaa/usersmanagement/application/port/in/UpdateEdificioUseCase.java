package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.domain.model.Edificio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface UpdateEdificioUseCase {
  Edificio execute(@NotNull UUID id, @NotNull @Valid UpdateEdificioCommand command);
}
