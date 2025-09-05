package com.sirkaue.hexagonalarchitecture.unit.domain.valueobjects;

import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.InvalidNameException;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void shouldCreateNameWhenValid() {
        String validName = "John Doe";
        Name name = new Name(validName);

        assertNotNull(name);
        assertEquals(validName.trim(), name.value());
    }

    @Test
    void shouldTrimSpacesFromName() {
        String nameWithSpaces = "  John Doe  ";
        Name name = new Name(nameWithSpaces);

        assertEquals("John Doe", name.value());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(InvalidNameException.class, () -> new Name(null));
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(InvalidNameException.class, () -> new Name(""));
        assertThrows(InvalidNameException.class, () -> new Name("   "));
    }

    @Test
    void shouldThrowExceptionWhenNameIsTooLong() {
        String longName = "a".repeat(101);
        assertThrows(InvalidNameException.class, () -> new Name(longName));
    }

    @Test
    void shouldAcceptNameWithSpecialCharacters() {
        String nameWithSpecialChars = "João d'Ávila-Santos Jr.";
        Name name = new Name(nameWithSpecialChars);

        assertEquals(nameWithSpecialChars, name.value());
    }
}
