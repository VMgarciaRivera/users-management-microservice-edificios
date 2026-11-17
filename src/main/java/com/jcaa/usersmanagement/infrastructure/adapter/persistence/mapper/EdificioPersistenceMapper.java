package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.Edificio;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.EdificioEntity;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class EdificioPersistenceMapper {

  public EdificioEntity fromModelToEntity(final Edificio edificio) {
    return new EdificioEntity(
        edificio.getId() != null ? edificio.getId().toString() : null,
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
        edificio.getTienzonaSocial(),
        null,
        null);
  }

  public Edificio fromEntityToModel(final EdificioEntity entity) {
    return Edificio.create(
        entity.id() != null ? UUID.fromString(entity.id()) : null,
        entity.nombre(),
        entity.metrosCuadrados(),
        entity.altura(),
        entity.numPisos(),
        entity.numApartamentos(),
        entity.numOficinas(),
        entity.nomParqueadero(),
        entity.numPisinas(),
        entity.pais(),
        entity.departamento(),
        entity.ciudad(),
        entity.tieneAsensor(),
        entity.valorAdministracion(),
        entity.tienzonaSocial());
  }

  public EdificioEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new EdificioEntity(
        resultSet.getString("id"),
        resultSet.getString("nombre"),
        resultSet.getDouble("metros_cuadrados"),
        resultSet.getDouble("altura"),
        resultSet.getInt("num_pisos"),
        resultSet.getInt("num_apartamentos"),
        resultSet.getInt("num_oficinas"),
        resultSet.getString("nom_parqueadero"),
        resultSet.getInt("num_pisinas"),
        resultSet.getString("pais"),
        resultSet.getString("departamento"),
        resultSet.getString("ciudad"),
        resultSet.getBoolean("tiene_asensor"),
        resultSet.getBigDecimal("valor_administracion"),
        resultSet.getBoolean("tien_zona_social"),
        resultSet.getString("created_at"),
        resultSet.getString("updated_at"));
  }

  public Edificio fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<Edificio> fromResultSetToModelList(final ResultSet resultSet) throws SQLException {
    final List<Edificio> edificios = new ArrayList<>();
    while (resultSet.next()) {
      edificios.add(fromResultSetToModel(resultSet));
    }
    return edificios;
  }
}
