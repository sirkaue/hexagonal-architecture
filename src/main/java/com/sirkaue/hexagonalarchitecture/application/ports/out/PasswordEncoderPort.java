package com.sirkaue.hexagonalarchitecture.application.ports.out;

public interface PasswordEncoderPort {

    String encode(String rawPassword);
}
