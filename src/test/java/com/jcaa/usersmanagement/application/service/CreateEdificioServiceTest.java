package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateEdificioService Test")
class CreateEdificioServiceTest {

  @Mock
  private EdificioRepositoryPort edificioRepositoryPort;

  private CreateEdificioService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new CreateEdificioService(edificioRepositoryPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() guarda exitosamente el edificio cuando el comando es válido")
  void shouldSaveEdificioSuccessfullyWhenCommandIsValid() {
    final UUID id = UUID.randomUUID();
    final CreateEdificioCommand command =
        new CreateEdificioCommand(
            id,
            "Torre Horizonte",
            1500.50,
            45.0,
            15,
            60,
            10,
            "Parqueadero Principal",
            2,
            "Colombia",
            "Antioquia",
            "Medellín",
            true,
            new BigDecimal("350000.00"),
            true);

    final Edificio savedEdificio =
        Edificio.builder()
            .id(id)
            .nombre("Torre Horizonte")
            .metrosCuadrados(1500.50)
            .altura(45.0)
            .numPisos(15)
            .numApartamentos(60)
            .numOficinas(10)
            .nomParqueadero("Parqueadero Principal")
            .numPisinas(2)
            .pais("Colombia")
            .departamento("Antioquia")
            .ciudad("Medellín")
            .tieneAsensor(true)
            .valorAdministracion(new BigDecimal("350000.00"))
            .tienzonaSocial(true)
            .build();

    when(edificioRepositoryPort.save(any(Edificio.class))).thenReturn(savedEdificio);

    final Edificio result = service.execute(command);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals("Torre Horizonte", result.getNombre());
    assertEquals(15, result.getNumPisos());

    final ArgumentCaptor<Edificio> captor = ArgumentCaptor.forClass(Edificio.class);
    verify(edificioRepositoryPort).save(captor.capture());
    final Edificio captured = captor.getValue();
    assertEquals("Torre Horizonte", captured.getNombre());
    assertEquals("Medellín", captured.getCiudad());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el comando tiene datos inválidos")
  void shouldThrowExceptionWhenCommandIsInvalid() {
    final CreateEdificioCommand invalidCommand =
        new CreateEdificioCommand(
            null,
            "", // nombre en blanco
            -10.0, // metros negativos
            0.0, // altura cero
            -1, // pisos negativos
            -5,
            -2,
            "",
            -1,
            "",
            "",
            "",
            null,
            new BigDecimal("-100"),
            null);

    assertThrows(ConstraintViolationException.class, () -> service.execute(invalidCommand));
    verifyNoInteractions(edificioRepositoryPort);
  }
}
