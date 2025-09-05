package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class SamePasswordException extends DomainException {

    public SamePasswordException() {
        super(DomainErrorCode.SAME_EMAIL);
    }
}
