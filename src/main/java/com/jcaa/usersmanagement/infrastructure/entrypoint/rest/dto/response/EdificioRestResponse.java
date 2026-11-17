package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record EdificioRestResponse(
    UUID id,
    String nombre,
    Double metrosCuadrados,
    Double altura,
    Integer numPisos,
    Integer numApartamentos,
    Integer numOficinas,
    String nomParqueadero,
    Integer numPisinas,
    String pais,
    String departamento,
    String ciudad,
    Boolean tieneAsensor,
    BigDecimal valorAdministracion,
    Boolean tienzonaSocial) {}
