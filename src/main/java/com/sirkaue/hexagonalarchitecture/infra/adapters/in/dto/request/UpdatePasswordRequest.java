package com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.request;

public record UpdatePasswordRequest(String currentPassword, String newPassword, String confirmPassword) {
}
