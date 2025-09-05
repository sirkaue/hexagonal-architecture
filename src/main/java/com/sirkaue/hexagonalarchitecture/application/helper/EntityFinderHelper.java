package com.sirkaue.hexagonalarchitecture.application.helper;

import com.sirkaue.hexagonalarchitecture.application.ports.out.FindUserByIdPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.UserNotFoundException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;

public class EntityFinderHelper {

    private final FindUserByIdPort findUserByIdPort;

    public EntityFinderHelper(FindUserByIdPort findUserByIdPort) {
        this.findUserByIdPort = findUserByIdPort;
    }

    public User findUserByIdOrThrow(Long id) {
        return findUserByIdPort.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }
}
