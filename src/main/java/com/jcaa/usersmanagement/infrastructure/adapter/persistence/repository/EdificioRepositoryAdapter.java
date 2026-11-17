package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.domain.model.Edificio;
import com.jcaa.usersmanagement.domain.port.out.EdificioRepositoryPort;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.EdificioEntity;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.EdificioPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador de Salida (Driven / Outbound Adapter) para la persistencia de Edificios.
 * Implementa el puerto {@link EdificioRepositoryPort} usando JDBC sobre MySQL.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class EdificioRepositoryAdapter implements EdificioRepositoryPort {

  private static final String SQL_INSERT =
      "INSERT INTO edificios "
          + "(id, nombre, metros_cuadrados, altura, num_pisos, num_apartamentos, num_oficinas, "
          + "nom_parqueadero, num_pisinas, pais, departamento, ciudad, tiene_asensor, "
          + "valor_administracion, tien_zona_social, created_at, updated_at) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

  private static final String SQL_SELECT_BY_ID =
      "SELECT id, nombre, metros_cuadrados, altura, num_pisos, num_apartamentos, num_oficinas, "
          + "nom_parqueadero, num_pisinas, pais, departamento, ciudad, tiene_asensor, "
          + "valor_administracion, tien_zona_social, created_at, updated_at "
          + "FROM edificios WHERE id = ? LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT id, nombre, metros_cuadrados, altura, num_pisos, num_apartamentos, num_oficinas, "
          + "nom_parqueadero, num_pisinas, pais, departamento, ciudad, tiene_asensor, "
          + "valor_administracion, tien_zona_social, created_at, updated_at "
          + "FROM edificios ORDER BY nombre ASC";

  private static final String SQL_UPDATE =
      "UPDATE edificios SET "
          + "nombre = ?, metros_cuadrados = ?, altura = ?, num_pisos = ?, num_apartamentos = ?, "
          + "num_oficinas = ?, nom_parqueadero = ?, num_pisinas = ?, pais = ?, departamento = ?, "
          + "ciudad = ?, tiene_asensor = ?, valor_administracion = ?, tien_zona_social = ?, updated_at = NOW() "
          + "WHERE id = ?";

  private static final String SQL_DELETE =
      "DELETE FROM edificios WHERE id = ?";

  private static final String SQL_EXISTS_BY_ID =
      "SELECT 1 FROM edificios WHERE id = ? LIMIT 1";

  private final DataSource dataSource;

  @Override
  public Edificio save(final Edificio edificio) {
    final EdificioEntity entity = EdificioPersistenceMapper.fromModelToEntity(edificio);
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {

      setStatementParameters(statement, entity);
      statement.setString(1, entity.id());
      statement.executeUpdate();

      return findById(edificio.getId()).orElse(edificio);
    } catch (final SQLException exception) {
      log.error("Error al persistir edificio con ID: {}", edificio.getId(), exception);
      throw PersistenceException.becauseSaveFailed(String.valueOf(edificio.getId()), exception);
    }
  }

  @Override
  public Optional<Edificio> findById(final UUID id) {
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {

      statement.setString(1, id.toString());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(EdificioPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      log.error("Error al consultar edificio por ID: {}", id, exception);
      throw PersistenceException.becauseFindByIdFailed(id.toString(), exception);
    }
  }

  @Override
  public List<Edificio> findAll() {
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
         final ResultSet resultSet = statement.executeQuery()) {

      return EdificioPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      log.error("Error al consultar todos los edificios", exception);
      throw PersistenceException.becauseFindAllFailed(exception);
    }
  }

  @Override
  public Edificio update(final Edificio edificio) {
    final EdificioEntity entity = EdificioPersistenceMapper.fromModelToEntity(edificio);
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

      statement.setString(1, entity.nombre());
      statement.setDouble(2, entity.metrosCuadrados());
      statement.setDouble(3, entity.altura());
      statement.setInt(4, entity.numPisos());
      statement.setInt(5, entity.numApartamentos());
      statement.setInt(6, entity.numOficinas());
      statement.setString(7, entity.nomParqueadero());
      statement.setInt(8, entity.numPisinas());
      statement.setString(9, entity.pais());
      statement.setString(10, entity.departamento());
      statement.setString(11, entity.ciudad());
      statement.setBoolean(12, entity.tieneAsensor());
      statement.setBigDecimal(13, entity.valorAdministracion());
      statement.setBoolean(14, entity.tienzonaSocial());
      statement.setString(15, entity.id());

      statement.executeUpdate();
      return findById(edificio.getId()).orElse(edificio);
    } catch (final SQLException exception) {
      log.error("Error al actualizar edificio con ID: {}", edificio.getId(), exception);
      throw PersistenceException.becauseUpdateFailed(String.valueOf(edificio.getId()), exception);
    }
  }

  @Override
  public void deleteById(final UUID id) {
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {

      statement.setString(1, id.toString());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      log.error("Error al eliminar edificio con ID: {}", id, exception);
      throw PersistenceException.becauseDeleteFailed(id.toString(), exception);
    }
  }

  @Override
  public boolean existsById(final UUID id) {
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_EXISTS_BY_ID)) {

      statement.setString(1, id.toString());
      final ResultSet resultSet = statement.executeQuery();
      return resultSet.next();
    } catch (final SQLException exception) {
      log.error("Error al verificar existencia de edificio con ID: {}", id, exception);
      throw PersistenceException.becauseFindByIdFailed(id.toString(), exception);
    }
  }

  private void setStatementParameters(final PreparedStatement statement, final EdificioEntity entity)
      throws SQLException {
    statement.setString(2, entity.nombre());
    statement.setDouble(3, entity.metrosCuadrados());
    statement.setDouble(4, entity.altura());
    statement.setInt(5, entity.numPisos());
    statement.setInt(6, entity.numApartamentos());
    statement.setInt(7, entity.numOficinas());
    statement.setString(8, entity.nomParqueadero());
    statement.setInt(9, entity.numPisinas());
    statement.setString(10, entity.pais());
    statement.setString(11, entity.departamento());
    statement.setString(12, entity.ciudad());
    statement.setBoolean(13, entity.tieneAsensor());
    statement.setBigDecimal(14, entity.valorAdministracion());
    statement.setBoolean(15, entity.tienzonaSocial());
  }
}
