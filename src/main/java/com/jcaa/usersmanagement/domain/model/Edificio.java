package com.jcaa.usersmanagement.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Modelo de dominio para la entidad Edificio.
 * Clase de dominio pura: sin dependencias de frameworks de persistencia (JPA, Hibernate, etc.).
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Edificio {

  private UUID id;
  private String nombre;
  private Double metrosCuadrados;
  private Double altura;
  private Integer numPisos;
  private Integer numApartamentos;
  private Integer numOficinas;
  private String nomParqueadero;
  private Integer numPisinas;
  private String pais;
  private String departamento;
  private String ciudad;
  private Boolean tieneAsensor;
  private BigDecimal valorAdministracion;
  private Boolean tienzonaSocial;

  public static Edificio create(
      final UUID id,
      final String nombre,
      final Double metrosCuadrados,
      final Double altura,
      final Integer numPisos,
      final Integer numApartamentos,
      final Integer numOficinas,
      final String nomParqueadero,
      final Integer numPisinas,
      final String pais,
      final String departamento,
      final String ciudad,
      final Boolean tieneAsensor,
      final BigDecimal valorAdministracion,
      final Boolean tienzonaSocial) {
    return Edificio.builder()
        .id(id != null ? id : UUID.randomUUID())
        .nombre(nombre)
        .metrosCuadrados(metrosCuadrados)
        .altura(altura)
        .numPisos(numPisos)
        .numApartamentos(numApartamentos)
        .numOficinas(numOficinas)
        .nomParqueadero(nomParqueadero)
        .numPisinas(numPisinas)
        .pais(pais)
        .departamento(departamento)
        .ciudad(ciudad)
        .tieneAsensor(tieneAsensor)
        .valorAdministracion(valorAdministracion)
        .tienzonaSocial(tienzonaSocial)
        .build();
  }
}
