package com.sirkaue.hexagonalarchitecture.application.ports.in;

import com.sirkaue.hexagonalarchitecture.domain.model.User;

public interface UpdateUserEmailUseCase {

    User execute(Long id, String newEmail);
}

