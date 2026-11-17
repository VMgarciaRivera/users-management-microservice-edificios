package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.domain.model.Edificio;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class EdificioApplicationMapper {

  public Edificio fromCreateCommandToModel(final CreateEdificioCommand command) {
    return Edificio.create(
        command.id() != null ? command.id() : UUID.randomUUID(),
        command.nombre(),
        command.metrosCuadrados(),
        command.altura(),
        command.numPisos(),
        command.numApartamentos(),
        command.numOficinas(),
        command.nomParqueadero(),
        command.numPisinas(),
        command.pais(),
        command.departamento(),
        command.ciudad(),
        command.tieneAsensor(),
        command.valorAdministracion(),
        command.tienzonaSocial());
  }

  public Edificio fromUpdateCommandToModel(final UUID id, final UpdateEdificioCommand command) {
    return Edificio.create(
        id,
        command.nombre(),
        command.metrosCuadrados(),
        command.altura(),
        command.numPisos(),
        command.numApartamentos(),
        command.numOficinas(),
        command.nomParqueadero(),
        command.numPisinas(),
        command.pais(),
        command.departamento(),
        command.ciudad(),
        command.tieneAsensor(),
        command.valorAdministracion(),
        command.tienzonaSocial());
  }
}
