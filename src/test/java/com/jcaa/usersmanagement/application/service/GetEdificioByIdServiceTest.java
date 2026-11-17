package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.Edificio;
import com.jcaa.usersmanagement.domain.port.out.EdificioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetEdificioByIdService Test")
class GetEdificioByIdServiceTest {

  @Mock
  private EdificioRepositoryPort edificioRepositoryPort;

  private GetEdificioByIdService service;

  @BeforeEach
  void setUp() {
    service = new GetEdificioByIdService(edificioRepositoryPort);
  }

  @Test
  @DisplayName("execute() retorna el edificio cuando existe")
  void shouldReturnEdificioWhenFound() {
    final UUID id = UUID.randomUUID();
    final Edificio edificio =
        Edificio.builder()
            .id(id)
            .nombre("Edificio Central")
            .metrosCuadrados(2500.0)
            .altura(60.0)
            .numPisos(20)
            .numApartamentos(80)
            .numOficinas(20)
            .nomParqueadero("Subterráneo")
            .numPisinas(1)
            .pais("Colombia")
            .departamento("Cundinamarca")
            .ciudad("Bogotá")
            .tieneAsensor(true)
            .valorAdministracion(new BigDecimal("450000.00"))
            .tienzonaSocial(true)
            .build();

    when(edificioRepositoryPort.findById(id)).thenReturn(Optional.of(edificio));

    final Edificio result = service.execute(id);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals("Edificio Central", result.getNombre());
    verify(edificioRepositoryPort).findById(id);
  }

  @Test
  @DisplayName("execute() lanza EdificioNotFoundException cuando el ID no existe")
  void shouldThrowNotFoundExceptionWhenIdDoesNotExist() {
    final UUID id = UUID.randomUUID();
    when(edificioRepositoryPort.findById(id)).thenReturn(Optional.empty());

    assertThrows(EdificioNotFoundException.class, () -> service.execute(id));
    verify(edificioRepositoryPort).findById(id);
  }
}
