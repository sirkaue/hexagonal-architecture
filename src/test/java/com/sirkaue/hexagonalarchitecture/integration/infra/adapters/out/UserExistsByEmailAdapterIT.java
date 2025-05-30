package com.sirkaue.hexagonalarchitecture.integration.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.infra.adapters.out.UserExistsByEmailAdapter;
import com.sirkaue.hexagonalarchitecture.integration.infra.adapters.support.helper.UserTestDataHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DataJpaTest
@Import({UserExistsByEmailAdapter.class, UserTestDataHelper.class})
public class UserExistsByEmailAdapterIT {

    @Autowired
    private UserExistsByEmailAdapter adapter;

    @Autowired
    private UserTestDataHelper helper;

    @Test
    void shouldReturnTrueWhenEmailExists() {
        // Arrange
        helper.persistDefaultUser();

        // Act
        boolean exists = adapter.existsByEmail("john@example.com");

        // Assert
        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {
        // Act
        boolean exists = adapter.existsByEmail("test@example.com");

        // Assert
        assertFalse(exists);
    }
}
