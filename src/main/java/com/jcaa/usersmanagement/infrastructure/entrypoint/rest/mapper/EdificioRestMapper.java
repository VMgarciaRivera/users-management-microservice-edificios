package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.domain.model.Edificio;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EdificioRestResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class EdificioRestMapper {

  public CreateEdificioCommand toCreateCommand(final CreateEdificioRestRequest request) {
    return new CreateEdificioCommand(
        request.id(),
        request.nombre(),
        request.metrosCuadrados(),
        request.altura(),
        request.numPisos(),
        request.numApartamentos(),
        request.numOficinas(),
        request.nomParqueadero(),
        request.numPisinas(),
        request.pais(),
        request.departamento(),
        request.ciudad(),
        request.tieneAsensor(),
        request.valorAdministracion(),
        request.tienzonaSocial());
  }

  public UpdateEdificioCommand toUpdateCommand(final UpdateEdificioRestRequest request) {
    return new UpdateEdificioCommand(
        request.nombre(),
        request.metrosCuadrados(),
        request.altura(),
        request.numPisos(),
        request.numApartamentos(),
        request.numOficinas(),
        request.nomParqueadero(),
        request.numPisinas(),
        request.pais(),
        request.departamento(),
        request.ciudad(),
        request.tieneAsensor(),
        request.valorAdministracion(),
        request.tienzonaSocial());
  }

  public EdificioRestResponse toResponse(final Edificio edificio) {
    return new EdificioRestResponse(
        edificio.getId(),
        edificio.getNombre(),
        edificio.getMetrosCuadrados(),
        edificio.getAltura(),
        edificio.getNumPisos(),
        edificio.getNumApartamentos(),
        edificio.getNumOficinas(),
        edificio.getNomParqueadero(),
        edificio.getNumPisinas(),
        edificio.getPais(),
        edificio.getDepartamento(),
        edificio.getCiudad(),
        edificio.getTieneAsensor(),
        edificio.getValorAdministracion(),
        edificio.getTienzonaSocial());
  }

  public List<EdificioRestResponse> toResponseList(final List<Edificio> edificios) {
    return edificios.stream().map(EdificioRestMapper::toResponse).toList();
  }
}
