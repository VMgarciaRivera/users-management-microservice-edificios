package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

import java.math.BigDecimal;

/**
 * Entidad de persistencia para Edificio.
 * Modela la estructura de la tabla 'edificios' en la base de datos MySQL.
 */
public record EdificioEntity(
    String id,
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
    Boolean tienzonaSocial,
    String createdAt,
    String updatedAt) {}
