package com.sirkaue.hexagonalarchitecture.integration.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.infra.adapters.out.PasswordEncoderAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(PasswordEncoderAdapter.class)
public class PasswordEncoderAdapterIT {

    @Autowired
    private PasswordEncoder encoder;

    @Test
    void shouldEncodeAndMatchPassword() {
        // Arrange
        String password = "password123";

        // Act
        String encoded = encoder.encode(password);

        assertNotEquals(password, encoded, "A senha codificada não deve ser igual à senha original");
        assertTrue(encoder.matches(password, encoded), "A senha original deve bater com o hash gerado");
    }
}
