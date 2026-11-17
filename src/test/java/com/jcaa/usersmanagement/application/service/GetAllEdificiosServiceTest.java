package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.domain.model.Edificio;
import com.jcaa.usersmanagement.domain.port.out.EdificioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetAllEdificiosService Test")
class GetAllEdificiosServiceTest {

  @Mock
  private EdificioRepositoryPort edificioRepositoryPort;

  private GetAllEdificiosService service;

  @BeforeEach
  void setUp() {
    service = new GetAllEdificiosService(edificioRepositoryPort);
  }

  @Test
  @DisplayName("execute() retorna la lista de todos los edificios")
  void shouldReturnAllEdificios() {
    final Edificio e1 = Edificio.builder()
        .id(UUID.randomUUID())
        .nombre("Edificio Norte")
        .metrosCuadrados(1200.0)
        .altura(30.0)
        .numPisos(10)
        .numApartamentos(40)
        .numOficinas(5)
        .nomParqueadero("Parqueadero Norte")
        .numPisinas(1)
        .pais("Colombia")
        .departamento("Atlántico")
        .ciudad("Barranquilla")
        .tieneAsensor(true)
        .valorAdministracion(new BigDecimal("280000.00"))
        .tienzonaSocial(true)
        .build();

    final Edificio e2 = Edificio.builder()
        .id(UUID.randomUUID())
        .nombre("Edificio Sur")
        .metrosCuadrados(1800.0)
        .altura(35.0)
        .numPisos(12)
        .numApartamentos(48)
        .numOficinas(8)
        .nomParqueadero("Parqueadero Sur")
        .numPisinas(0)
        .pais("Colombia")
        .departamento("Valle del Cauca")
        .ciudad("Cali")
        .tieneAsensor(true)
        .valorAdministracion(new BigDecimal("310000.00"))
        .tienzonaSocial(false)
        .build();

    when(edificioRepositoryPort.findAll()).thenReturn(List.of(e1, e2));

    final List<Edificio> result = service.execute();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Edificio Norte", result.get(0).getNombre());
    assertEquals("Edificio Sur", result.get(1).getNombre());
    verify(edificioRepositoryPort).findAll();
  }
}
