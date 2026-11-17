package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.Edificio;
import com.jcaa.usersmanagement.domain.port.out.EdificioRepositoryPort;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateEdificioService Test")
class UpdateEdificioServiceTest {

  @Mock
  private EdificioRepositoryPort edificioRepositoryPort;

  private UpdateEdificioService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new UpdateEdificioService(edificioRepositoryPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() actualiza exitosamente el edificio cuando existe y el comando es válido")
  void shouldUpdateEdificioSuccessfullyWhenExistsAndValid() {
    final UUID id = UUID.randomUUID();
    final UpdateEdificioCommand command =
        new UpdateEdificioCommand(
            "Torre Horizonte Renovada",
            1600.0,
            48.0,
            16,
            64,
            12,
            "Parqueadero VIP",
            3,
            "Colombia",
            "Antioquia",
            "Medellín",
            true,
            new BigDecimal("390000.00"),
            true);

    final Edificio updatedEdificio =
        Edificio.builder()
            .id(id)
            .nombre("Torre Horizonte Renovada")
            .metrosCuadrados(1600.0)
            .altura(48.0)
            .numPisos(16)
            .numApartamentos(64)
            .numOficinas(12)
            .nomParqueadero("Parqueadero VIP")
            .numPisinas(3)
            .pais("Colombia")
            .departamento("Antioquia")
            .ciudad("Medellín")
            .tieneAsensor(true)
            .valorAdministracion(new BigDecimal("390000.00"))
            .tienzonaSocial(true)
            .build();

    when(edificioRepositoryPort.existsById(id)).thenReturn(true);
    when(edificioRepositoryPort.update(any(Edificio.class))).thenReturn(updatedEdificio);

    final Edificio result = service.execute(id, command);

    assertNotNull(result);
    assertEquals("Torre Horizonte Renovada", result.getNombre());
    assertEquals(16, result.getNumPisos());

    final ArgumentCaptor<Edificio> captor = ArgumentCaptor.forClass(Edificio.class);
    verify(edificioRepositoryPort).update(captor.capture());
    final Edificio captured = captor.getValue();
    assertEquals(id, captured.getId());
    assertEquals("Torre Horizonte Renovada", captured.getNombre());
  }

  @Test
  @DisplayName("execute() lanza EdificioNotFoundException cuando el ID no existe")
  void shouldThrowNotFoundExceptionWhenEdificioDoesNotExist() {
    final UUID id = UUID.randomUUID();
    final UpdateEdificioCommand command =
        new UpdateEdificioCommand(
            "Torre Inexistente",
            1000.0,
            30.0,
            10,
            20,
            5,
            "Parqueadero",
            1,
            "Colombia",
            "Antioquia",
            "Medellín",
            true,
            new BigDecimal("200000.00"),
            true);

    when(edificioRepositoryPort.existsById(id)).thenReturn(false);

    assertThrows(EdificioNotFoundException.class, () -> service.execute(id, command));
    verify(edificioRepositoryPort, never()).update(any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando los datos del comando son inválidos")
  void shouldThrowExceptionWhenCommandIsInvalid() {
    final UUID id = UUID.randomUUID();
    final UpdateEdificioCommand invalidCommand =
        new UpdateEdificioCommand(
            "",
            -1.0,
            -1.0,
            0,
            -1,
            -1,
            "",
            -1,
            "",
            "",
            "",
            null,
            null,
            null);

    assertThrows(ConstraintViolationException.class, () -> service.execute(id, invalidCommand));
    verifyNoInteractions(edificioRepositoryPort);
  }
}
