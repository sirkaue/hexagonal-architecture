package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.ports.in.FindUserByIdUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.FindUserByIdPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.UserNotFoundException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;

public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    private final FindUserByIdPort findUserByIdPort;

    public FindUserByIdUseCaseImpl(FindUserByIdPort findUserByIdPort) {
        this.findUserByIdPort = findUserByIdPort;
    }

    @Override
    public User execute(Long id) {
        return findUserByIdPort.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }
}
