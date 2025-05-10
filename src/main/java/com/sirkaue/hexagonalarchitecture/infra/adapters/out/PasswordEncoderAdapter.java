package com.sirkaue.hexagonalarchitecture.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.application.ports.out.PasswordEncoderPort;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(Password rawPassword) {
        return passwordEncoder.encode(rawPassword.value());
    }

    @Override
    public boolean matches(Password rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword.value(), encodedPassword);
    }
}
