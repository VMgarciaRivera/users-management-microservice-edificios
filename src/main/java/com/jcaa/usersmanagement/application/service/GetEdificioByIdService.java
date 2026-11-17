package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetEdificioByIdUseCase;
import com.jcaa.usersmanagement.domain.port.out.EdificioRepositoryPort;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.Edificio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetEdificioByIdService implements GetEdificioByIdUseCase {

  private final EdificioRepositoryPort edificioRepositoryPort;

  @Override
  public Edificio execute(final UUID id) {
    log.info("Consultando edificio por ID: {}", id);
    return edificioRepositoryPort
        .findById(id)
        .orElseThrow(() -> EdificioNotFoundException.becauseIdWasNotFound(id));
  }
}
