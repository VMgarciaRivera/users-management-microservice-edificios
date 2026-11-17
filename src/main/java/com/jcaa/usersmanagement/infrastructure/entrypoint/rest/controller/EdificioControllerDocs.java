package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.infrastructure.config.OpenApiConfig;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EdificioRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Edificios", description = "Gestión de edificios: crear, consultar, actualizar y eliminar.")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
public interface EdificioControllerDocs {

  @Operation(summary = "Crear edificio", description = "Registra un nuevo edificio en el sistema.")
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "Edificio creado exitosamente.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = EdificioRestResponse.class))),
      @ApiResponse(
          responseCode = "400",
          description = "Datos inválidos en el cuerpo de la petición.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
      @ApiResponse(
          responseCode = "401",
          description = "No autenticado. Se requiere un token Bearer JWT válido.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  EdificioRestResponse create(@Valid @RequestBody CreateEdificioRestRequest request);

  @Operation(summary = "Consultar todos los edificios", description = "Retorna la lista de todos los edificios registrados.")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Lista de edificios obtenida exitosamente.",
          content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EdificioRestResponse.class)))),
      @ApiResponse(
          responseCode = "401",
          description = "No autenticado. Se requiere un token Bearer JWT válido.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  List<EdificioRestResponse> getAll();

  @Operation(summary = "Consultar edificio por ID", description = "Retorna un edificio específico según su identificador único (UUID).")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Edificio encontrado.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = EdificioRestResponse.class))),
      @ApiResponse(
          responseCode = "401",
          description = "No autenticado. Se requiere un token Bearer JWT válido.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
      @ApiResponse(
          responseCode = "404",
          description = "Edificio no encontrado.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  EdificioRestResponse getById(@Parameter(description = "UUID del edificio", required = true) @PathVariable UUID id);

  @Operation(summary = "Actualizar edificio", description = "Actualiza la información de un edificio existente.")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Edificio actualizado exitosamente.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = EdificioRestResponse.class))),
      @ApiResponse(
          responseCode = "400",
          description = "Datos inválidos en la petición.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
      @ApiResponse(
          responseCode = "401",
          description = "No autenticado. Se requiere un token Bearer JWT válido.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
      @ApiResponse(
          responseCode = "404",
          description = "Edificio no encontrado.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  EdificioRestResponse update(
      @Parameter(description = "UUID del edificio", required = true) @PathVariable UUID id,
      @Valid @RequestBody UpdateEdificioRestRequest request);

  @Operation(summary = "Eliminar edificio", description = "Elimina un edificio por su ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Edificio eliminado exitosamente."),
      @ApiResponse(
          responseCode = "401",
          description = "No autenticado. Se requiere un token Bearer JWT válido.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
      @ApiResponse(
          responseCode = "404",
          description = "Edificio no encontrado.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  void delete(@Parameter(description = "UUID del edificio", required = true) @PathVariable UUID id);
}
