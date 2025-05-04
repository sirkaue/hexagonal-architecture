package com.sirkaue.hexagonalarchitecture.application.dto.request;

public record UpdatePasswordRequest(String currentPassword, String newPassword, String confirmPassword) {
}
