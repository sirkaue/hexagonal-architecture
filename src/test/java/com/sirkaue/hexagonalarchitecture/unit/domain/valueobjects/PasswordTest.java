package com.sirkaue.hexagonalarchitecture.unit.domain.valueobjects;

import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.InvalidPasswordException;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void shouldCreatePasswordWhenValid() {
        String validPassword = "validPass123";
        Password password = new Password(validPassword);

        assertNotNull(validPassword);
        assertEquals(validPassword, password.value());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsTooShort() {
        assertThrows(InvalidPasswordException.class, () -> new Password("123"));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        assertThrows(InvalidPasswordException.class, () -> new Password(null));
    }
}