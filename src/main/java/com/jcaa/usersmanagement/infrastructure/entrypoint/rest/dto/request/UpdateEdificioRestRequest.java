package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateEdificioRestRequest(
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    @NotNull(message = "Los metros cuadrados son obligatorios") @Positive(message = "Los metros cuadrados deben ser mayores a cero") Double metrosCuadrados,
    @NotNull(message = "La altura es obligatoria") @Positive(message = "La altura debe ser mayor a cero") Double altura,
    @NotNull(message = "El número de pisos es obligatorio") @Positive(message = "El número de pisos debe ser mayor a cero") Integer numPisos,
    @NotNull(message = "El número de apartamentos es obligatorio") @PositiveOrZero(message = "El número de apartamentos no puede ser negativo") Integer numApartamentos,
    @NotNull(message = "El número de oficinas es obligatorio") @PositiveOrZero(message = "El número de oficinas no puede ser negativo") Integer numOficinas,
    @NotBlank(message = "El nombre del parqueadero es obligatorio") String nomParqueadero,
    @NotNull(message = "El número de piscinas es obligatorio") @PositiveOrZero(message = "El número de piscinas no puede ser negativo") Integer numPisinas,
    @NotBlank(message = "El país es obligatorio") String pais,
    @NotBlank(message = "El departamento es obligatorio") String departamento,
    @NotBlank(message = "La ciudad es obligatoria") String ciudad,
    @NotNull(message = "Debe indicar si tiene ascensor") Boolean tieneAsensor,
    @NotNull(message = "El valor de administración es obligatorio") @PositiveOrZero(message = "El valor de administración no puede ser negativo") BigDecimal valorAdministracion,
    @NotNull(message = "Debe indicar si tiene zona social") Boolean tienzonaSocial) {}
