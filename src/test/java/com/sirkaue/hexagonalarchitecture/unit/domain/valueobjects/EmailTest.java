package com.sirkaue.hexagonalarchitecture.unit.domain.valueobjects;

import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.InvalidEmailException;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void shouldCreateEmailWhenValid() {
        String validEmail = "user@example.com";
        Email email = new Email(validEmail);

        assertNotNull(email);
        assertEquals(validEmail, email.value());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThrows(InvalidEmailException.class, () -> new Email(null));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(InvalidEmailException.class, () -> new Email("invalid-email"));
        assertThrows(InvalidEmailException.class, () -> new Email("no-at-sign.com"));
        assertThrows(InvalidEmailException.class, () -> new Email("no-domain@"));
    }
}
