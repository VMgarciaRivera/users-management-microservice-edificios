package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateEdificioUseCase;
import com.jcaa.usersmanagement.domain.port.out.EdificioRepositoryPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.Edificio;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateEdificioService implements UpdateEdificioUseCase {

  private final EdificioRepositoryPort edificioRepositoryPort;
  private final Validator validator;

  @Override
  public Edificio execute(final UUID id, final UpdateEdificioCommand command) {
    validateCommand(command);

    if (!edificioRepositoryPort.existsById(id)) {
      throw EdificioNotFoundException.becauseIdWasNotFound(id);
    }

    final Edificio edificioToUpdate = EdificioApplicationMapper.fromUpdateCommandToModel(id, command);
    log.info("Actualizando edificio con ID: {}", id);

    return edificioRepositoryPort.update(edificioToUpdate);
  }

  private void validateCommand(final UpdateEdificioCommand command) {
    final Set<ConstraintViolation<UpdateEdificioCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
