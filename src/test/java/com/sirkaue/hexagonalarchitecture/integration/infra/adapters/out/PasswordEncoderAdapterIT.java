package com.sirkaue.hexagonalarchitecture.integration.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.PasswordEncoderAdapter;
import com.sirkaue.hexagonalarchitecture.infra.config.PasswordEncoderConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig({PasswordEncoderAdapter.class, PasswordEncoderConfig.class})
public class PasswordEncoderAdapterIT {

    @Autowired
    private PasswordEncoderAdapter adapter;

    @Test
    void shouldEncodeAndMatchPassword() {
        // Arrange
        String password = "password123";

        // Act
        String encoded = adapter.encode(new Password(password));

        assertNotEquals(password, encoded, "A senha codificada não deve ser igual à senha original");
        assertTrue(adapter.matches(new Password(password), encoded), "A senha original deve bater com o hash gerado");
    }
}
