package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllEdificiosUseCase;
import com.jcaa.usersmanagement.domain.port.out.EdificioRepositoryPort;
import com.jcaa.usersmanagement.domain.model.Edificio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllEdificiosService implements GetAllEdificiosUseCase {

  private final EdificioRepositoryPort edificioRepositoryPort;

  @Override
  public List<Edificio> execute() {
    log.info("Consultando todos los edificios");
    return edificioRepositoryPort.findAll();
  }
}
