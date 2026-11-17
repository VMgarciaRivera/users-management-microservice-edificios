package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.Edificio;

import java.util.List;

public interface GetAllEdificiosUseCase {
  List<Edificio> execute();
}
