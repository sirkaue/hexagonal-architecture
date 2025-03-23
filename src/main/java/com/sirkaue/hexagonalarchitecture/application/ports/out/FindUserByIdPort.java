package com.sirkaue.hexagonalarchitecture.application.ports.out;

import com.sirkaue.hexagonalarchitecture.domain.model.User;

import java.util.Optional;

public interface FindUserByIdPort {

    Optional<User> findById(Long id);
}
