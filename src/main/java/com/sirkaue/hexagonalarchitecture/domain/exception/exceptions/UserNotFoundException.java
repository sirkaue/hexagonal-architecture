package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class UserNotFoundException extends DomainException {

    public UserNotFoundException() {
        super(DomainErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(Long id) {
        super(DomainErrorCode.USER_NOT_FOUND, String.format("User with id %s not found", id));
    }
}
