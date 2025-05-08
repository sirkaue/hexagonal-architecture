package com.sirkaue.hexagonalarchitecture.application.ports.out;

import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;

public interface PasswordEncoderPort {

    String encode(Password rawPassword);

    boolean matches(Password rawPassword, String encodedPassword);
}
