package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.CreateEdificioUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteEdificioUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllEdificiosUseCase;
import com.jcaa.usersmanagement.application.port.in.GetEdificioByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateEdificioUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.domain.model.Edificio;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EdificioRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper.EdificioRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Adaptador de Entrada (Driving / Inbound Adapter) para la API REST de Edificios.
 * Implementa las operaciones CRUD comunicándose exclusivamente con los Puertos de Entrada (Use Cases).
 */
@RestController
@RequestMapping("/api/edificios")
@RequiredArgsConstructor
public class EdificioController implements EdificioControllerDocs {

  private final CreateEdificioUseCase createEdificioUseCase;
  private final GetEdificioByIdUseCase getEdificioByIdUseCase;
  private final GetAllEdificiosUseCase getAllEdificiosUseCase;
  private final UpdateEdificioUseCase updateEdificioUseCase;
  private final DeleteEdificioUseCase deleteEdificioUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public EdificioRestResponse create(@Valid @RequestBody final CreateEdificioRestRequest request) {
    final CreateEdificioCommand command = EdificioRestMapper.toCreateCommand(request);
    final Edificio edificio = createEdificioUseCase.execute(command);
    return EdificioRestMapper.toResponse(edificio);
  }

  @GetMapping
  @Override
  public List<EdificioRestResponse> getAll() {
    return EdificioRestMapper.toResponseList(getAllEdificiosUseCase.execute());
  }

  @GetMapping("/{id}")
  @Override
  public EdificioRestResponse getById(@PathVariable final UUID id) {
    final Edificio edificio = getEdificioByIdUseCase.execute(id);
    return EdificioRestMapper.toResponse(edificio);
  }

  @PutMapping("/{id}")
  @Override
  public EdificioRestResponse update(
      @PathVariable final UUID id,
      @Valid @RequestBody final UpdateEdificioRestRequest request) {
    final UpdateEdificioCommand command = EdificioRestMapper.toUpdateCommand(request);
    final Edificio edificio = updateEdificioUseCase.execute(id, command);
    return EdificioRestMapper.toResponse(edificio);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Override
  public void delete(@PathVariable final UUID id) {
    deleteEdificioUseCase.execute(id);
  }
}
