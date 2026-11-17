package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateEdificioUseCase;
import com.jcaa.usersmanagement.domain.port.out.EdificioRepositoryPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.model.Edificio;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateEdificioService implements CreateEdificioUseCase {

  private final EdificioRepositoryPort edificioRepositoryPort;
  private final Validator validator;

  @Override
  public Edificio execute(final CreateEdificioCommand command) {
    validateCommand(command);

    final Edificio edificio = EdificioApplicationMapper.fromCreateCommandToModel(command);
    log.info("Creando nuevo edificio con nombre: {}", edificio.getNombre());

    return edificioRepositoryPort.save(edificio);
  }

  private void validateCommand(final CreateEdificioCommand command) {
    final Set<ConstraintViolation<CreateEdificioCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
