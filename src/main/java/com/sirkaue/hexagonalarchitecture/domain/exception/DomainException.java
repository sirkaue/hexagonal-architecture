package com.sirkaue.hexagonalarchitecture.domain.exception;

public abstract class DomainException extends RuntimeException {

    private final DomainErrorCode domainErrorCode;

    protected DomainException(DomainErrorCode domainErrorCode) {
        super(domainErrorCode.getDefaultMessage());
        this.domainErrorCode = domainErrorCode;
    }

    protected DomainException(DomainErrorCode domainErrorCode, String customMessage) {
        super(customMessage);
        this.domainErrorCode = domainErrorCode;
    }

    public DomainErrorCode getErrorCode() {
        return domainErrorCode;
    }
}
