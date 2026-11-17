package com.jcaa.usersmanagement.domain.port.out;

import com.jcaa.usersmanagement.domain.model.Edificio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de Salida (Output Port) para la persistencia de Edificios.
 * Define el contrato que la capa de infraestructura debe implementar.
 */
public interface EdificioRepositoryPort {

  Edificio save(Edificio edificio);

  Optional<Edificio> findById(UUID id);

  List<Edificio> findAll();

  Edificio update(Edificio edificio);

  void deleteById(UUID id);

  boolean existsById(UUID id);
}
