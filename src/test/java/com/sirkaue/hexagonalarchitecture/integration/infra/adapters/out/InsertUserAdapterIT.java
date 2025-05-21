package com.sirkaue.hexagonalarchitecture.integration.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.InsertUserAdapter;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper.impl.UserEntityMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@DataJpaTest
@Import({InsertUserAdapter.class, UserEntityMapperImpl.class})
public class InsertUserAdapterIT {

    @Autowired
    private InsertUserAdapter adapter;

    @Test
    void shouldInsertUserWhenIdDoesNotExist() {
        // Arrange
        User user = new User(null, "John Doe", new Email("john@example.com"), new Password("password123"));

        // Act
        User insertedUser = adapter.insert(user);

        // Assert
        assertNotNull(insertedUser.getId());
        assertEquals("John Doe", insertedUser.getName());
        assertEquals("john@example.com", insertedUser.getEmail().value());
        assertEquals("password123", insertedUser.getPassword().value());
    }
}
