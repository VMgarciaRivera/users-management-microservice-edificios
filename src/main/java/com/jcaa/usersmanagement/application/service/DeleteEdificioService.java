package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteEdificioUseCase;
import com.jcaa.usersmanagement.domain.port.out.EdificioRepositoryPort;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteEdificioService implements DeleteEdificioUseCase {

  private final EdificioRepositoryPort edificioRepositoryPort;

  @Override
  public void execute(final UUID id) {
    log.info("Eliminando edificio con ID: {}", id);

    if (!edificioRepositoryPort.existsById(id)) {
      throw EdificioNotFoundException.becauseIdWasNotFound(id);
    }

    edificioRepositoryPort.deleteById(id);
  }
}
