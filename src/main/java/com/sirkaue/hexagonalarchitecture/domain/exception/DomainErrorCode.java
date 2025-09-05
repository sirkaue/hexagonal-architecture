package com.sirkaue.hexagonalarchitecture.domain.exception;

public enum DomainErrorCode {

    EMAIL_ALREADY_EXISTS("Email already exists"),
    SAME_EMAIL("The new email is the same as the current one"),
    USER_NOT_FOUND("User not found"),
    INCORRECT_CURRENT_PASSWORD("The current password is incorrect"),
    INVALID_EMAIL("Invalid email format"),
    NAME_BLANK("Name cannot be null or blank"),
    NAME_TOO_LONG("Name must not exceed 100 characters"),
    INVALID_NAME("Invalid name"),
    INVALID_PASSWORD("Password must be at least 6 characters"),
    PASSWORD_CONFIRMATION("Password confirmation does not match"),
    SAME_PASSWORD("The new password is the same as the current one");

    private final String defaultMessage;

    DomainErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
