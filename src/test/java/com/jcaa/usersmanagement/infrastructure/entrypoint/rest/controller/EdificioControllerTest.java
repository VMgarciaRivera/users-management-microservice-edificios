package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.CreateEdificioUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteEdificioUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllEdificiosUseCase;
import com.jcaa.usersmanagement.application.port.in.GetEdificioByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateEdificioUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.Edificio;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.advice.GlobalExceptionHandler;
import com.jcaa.usersmanagement.infrastructure.security.JwtAuthenticationFilter;
import com.jcaa.usersmanagement.infrastructure.security.JwtTokenService;
import com.jcaa.usersmanagement.infrastructure.security.RestAccessDeniedHandler;
import com.jcaa.usersmanagement.infrastructure.security.RestAuthenticationEntryPoint;
import com.jcaa.usersmanagement.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = EdificioController.class,
    properties = "spring.main.web-application-type=servlet")
@Import({
    SecurityConfig.class,
    JwtAuthenticationFilter.class,
    RestAuthenticationEntryPoint.class,
    RestAccessDeniedHandler.class,
    GlobalExceptionHandler.class
})
@DisplayName("EdificioController Integration Tests")
class EdificioControllerTest {

  private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CreateEdificioUseCase createEdificioUseCase;

  @MockBean
  private GetEdificioByIdUseCase getEdificioByIdUseCase;

  @MockBean
  private GetAllEdificiosUseCase getAllEdificiosUseCase;

  @MockBean
  private UpdateEdificioUseCase updateEdificioUseCase;

  @MockBean
  private DeleteEdificioUseCase deleteEdificioUseCase;

  @MockBean
  private JwtTokenService jwtTokenService;

  private Edificio createSampleEdificio() {
    return Edificio.builder()
        .id(ID)
        .nombre("Torre Cristal")
        .metrosCuadrados(2500.50)
        .altura(75.0)
        .numPisos(22)
        .numApartamentos(88)
        .numOficinas(15)
        .nomParqueadero("Estacionamiento Subterráneo")
        .numPisinas(2)
        .pais("Colombia")
        .departamento("Antioquia")
        .ciudad("Medellín")
        .tieneAsensor(true)
        .valorAdministracion(new BigDecimal("420000.00"))
        .tienzonaSocial(true)
        .build();
  }

  @Test
  @WithMockUser
  @DisplayName("POST /api/edificios crea un edificio exitosamente y retorna 201 Created")
  void shouldCreateEdificioSuccessfully() throws Exception {
    final String requestBody =
        """
        {
          "id": "%s",
          "nombre": "Torre Cristal",
          "metrosCuadrados": 2500.50,
          "altura": 75.0,
          "numPisos": 22,
          "numApartamentos": 88,
          "numOficinas": 15,
          "nomParqueadero": "Estacionamiento Subterráneo",
          "numPisinas": 2,
          "pais": "Colombia",
          "departamento": "Antioquia",
          "ciudad": "Medellín",
          "tieneAsensor": true,
          "valorAdministracion": 420000.00,
          "tienzonaSocial": true
        }
        """.formatted(ID);

    final Edificio edificio = createSampleEdificio();
    when(createEdificioUseCase.execute(any(CreateEdificioCommand.class))).thenReturn(edificio);

    mockMvc
        .perform(post("/api/edificios").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(ID.toString()))
        .andExpect(jsonPath("$.nombre").value("Torre Cristal"))
        .andExpect(jsonPath("$.metrosCuadrados").value(2500.50))
        .andExpect(jsonPath("$.altura").value(75.0))
        .andExpect(jsonPath("$.numPisos").value(22))
        .andExpect(jsonPath("$.ciudad").value("Medellín"))
        .andExpect(jsonPath("$.tieneAsensor").value(true));

    verify(createEdificioUseCase).execute(any(CreateEdificioCommand.class));
  }

  @Test
  @WithMockUser
  @DisplayName("POST /api/edificios retorna 400 Bad Request cuando el body es inválido")
  void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
    final String invalidBody =
        """
        {
          "nombre": "",
          "metrosCuadrados": -100.0,
          "altura": 0.0,
          "numPisos": -5
        }
        """;

    mockMvc
        .perform(post("/api/edificios").contentType(MediaType.APPLICATION_JSON).content(invalidBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400));
  }

  @Test
  @WithMockUser
  @DisplayName("GET /api/edificios retorna lista de edificios y 200 OK")
  void shouldGetAllEdificios() throws Exception {
    final Edificio edificio = createSampleEdificio();
    when(getAllEdificiosUseCase.execute()).thenReturn(List.of(edificio));

    mockMvc
        .perform(get("/api/edificios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(ID.toString()))
        .andExpect(jsonPath("$[0].nombre").value("Torre Cristal"))
        .andExpect(jsonPath("$[0].numApartamentos").value(88));

    verify(getAllEdificiosUseCase).execute();
  }

  @Test
  @WithMockUser
  @DisplayName("GET /api/edificios/{id} retorna el edificio encontrado y 200 OK")
  void shouldGetEdificioByIdSuccessfully() throws Exception {
    final Edificio edificio = createSampleEdificio();
    when(getEdificioByIdUseCase.execute(ID)).thenReturn(edificio);

    mockMvc
        .perform(get("/api/edificios/" + ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(ID.toString()))
        .andExpect(jsonPath("$.nombre").value("Torre Cristal"))
        .andExpect(jsonPath("$.pais").value("Colombia"));

    verify(getEdificioByIdUseCase).execute(ID);
  }

  @Test
  @WithMockUser
  @DisplayName("GET /api/edificios/{id} retorna 404 Not Found cuando no existe")
  void shouldReturnNotFoundWhenEdificioDoesNotExist() throws Exception {
    when(getEdificioByIdUseCase.execute(ID)).thenThrow(EdificioNotFoundException.becauseIdWasNotFound(ID));

    mockMvc
        .perform(get("/api/edificios/" + ID))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));

    verify(getEdificioByIdUseCase).execute(ID);
  }

  @Test
  @WithMockUser
  @DisplayName("PUT /api/edificios/{id} actualiza el edificio y retorna 200 OK")
  void shouldUpdateEdificioSuccessfully() throws Exception {
    final String requestBody =
        """
        {
          "nombre": "Torre Cristal Plaza",
          "metrosCuadrados": 2800.0,
          "altura": 80.0,
          "numPisos": 24,
          "numApartamentos": 96,
          "numOficinas": 18,
          "nomParqueadero": "Estacionamiento Plaza",
          "numPisinas": 2,
          "pais": "Colombia",
          "departamento": "Antioquia",
          "ciudad": "Medellín",
          "tieneAsensor": true,
          "valorAdministracion": 460000.00,
          "tienzonaSocial": true
        }
        """;

    final Edificio updated =
        Edificio.builder()
            .id(ID)
            .nombre("Torre Cristal Plaza")
            .metrosCuadrados(2800.0)
            .altura(80.0)
            .numPisos(24)
            .numApartamentos(96)
            .numOficinas(18)
            .nomParqueadero("Estacionamiento Plaza")
            .numPisinas(2)
            .pais("Colombia")
            .departamento("Antioquia")
            .ciudad("Medellín")
            .tieneAsensor(true)
            .valorAdministracion(new BigDecimal("460000.00"))
            .tienzonaSocial(true)
            .build();

    when(updateEdificioUseCase.execute(eq(ID), any(UpdateEdificioCommand.class))).thenReturn(updated);

    mockMvc
        .perform(put("/api/edificios/" + ID).contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(ID.toString()))
        .andExpect(jsonPath("$.nombre").value("Torre Cristal Plaza"))
        .andExpect(jsonPath("$.numPisos").value(24));

    verify(updateEdificioUseCase).execute(eq(ID), any(UpdateEdificioCommand.class));
  }

  @Test
  @WithMockUser
  @DisplayName("DELETE /api/edificios/{id} elimina el edificio y retorna 204 No Content")
  void shouldDeleteEdificioSuccessfully() throws Exception {
    doNothing().when(deleteEdificioUseCase).execute(ID);

    mockMvc
        .perform(delete("/api/edificios/" + ID))
        .andExpect(status().isNoContent());

    verify(deleteEdificioUseCase).execute(ID);
  }

  @Test
  @WithMockUser
  @DisplayName("DELETE /api/edificios/{id} retorna 404 Not Found cuando el edificio no existe")
  void shouldReturnNotFoundOnDeleteWhenEdificioDoesNotExist() throws Exception {
    doThrow(EdificioNotFoundException.becauseIdWasNotFound(ID)).when(deleteEdificioUseCase).execute(ID);

    mockMvc
        .perform(delete("/api/edificios/" + ID))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));

    verify(deleteEdificioUseCase).execute(ID);
  }

  @Test
  @DisplayName("Peticiones sin token a /api/edificios deben retornar 401 Unauthorized")
  void shouldRejectUnauthenticatedRequests() throws Exception {
    mockMvc
        .perform(get("/api/edificios"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status").value(401));

    mockMvc
        .perform(get("/api/edificios/" + ID))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status").value(401));

    mockMvc
        .perform(post("/api/edificios").contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status").value(401));

    mockMvc
        .perform(put("/api/edificios/" + ID).contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status").value(401));

    mockMvc
        .perform(delete("/api/edificios/" + ID))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status").value(401));
  }
}
