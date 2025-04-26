package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.ports.in.FindUserByIdUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.FindUserByIdPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.UserNotFoundException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    private static final Logger log = LoggerFactory.getLogger(FindUserByIdUseCaseImpl.class);

    private final FindUserByIdPort findUserByIdPort;

    public FindUserByIdUseCaseImpl(FindUserByIdPort findUserByIdPort) {
        this.findUserByIdPort = findUserByIdPort;
    }

    @Override
    public User execute(Long id) {
        log.info("Finding user with id: {}", id);
        return findUserByIdPort.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }
}
