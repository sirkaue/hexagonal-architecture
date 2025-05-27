package com.sirkaue.hexagonalarchitecture.application.ports.in;

import com.sirkaue.hexagonalarchitecture.domain.model.User;

public interface InsertUserUseCase {

    User execute(User user);
}
