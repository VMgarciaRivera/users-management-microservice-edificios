package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.port.out.EdificioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteEdificioService Test")
class DeleteEdificioServiceTest {

  @Mock
  private EdificioRepositoryPort edificioRepositoryPort;

  private DeleteEdificioService service;

  @BeforeEach
  void setUp() {
    service = new DeleteEdificioService(edificioRepositoryPort);
  }

  @Test
  @DisplayName("execute() elimina el edificio cuando existe")
  void shouldDeleteEdificioWhenExists() {
    final UUID id = UUID.randomUUID();
    when(edificioRepositoryPort.existsById(id)).thenReturn(true);

    assertDoesNotThrow(() -> service.execute(id));
    verify(edificioRepositoryPort).deleteById(id);
  }

  @Test
  @DisplayName("execute() lanza EdificioNotFoundException cuando el edificio no existe")
  void shouldThrowNotFoundExceptionWhenEdificioDoesNotExist() {
    final UUID id = UUID.randomUUID();
    when(edificioRepositoryPort.existsById(id)).thenReturn(false);

    assertThrows(EdificioNotFoundException.class, () -> service.execute(id));
    verify(edificioRepositoryPort, never()).deleteById(id);
  }
}
