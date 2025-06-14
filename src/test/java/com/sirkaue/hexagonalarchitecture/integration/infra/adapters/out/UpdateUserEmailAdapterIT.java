package com.sirkaue.hexagonalarchitecture.integration.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Name;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.UpdateUserEmailAdapter;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper.impl.UserEntityMapperImpl;
import com.sirkaue.hexagonalarchitecture.integration.infra.adapters.support.helper.UserTestDataHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
@Import({UpdateUserEmailAdapter.class, UserEntityMapperImpl.class, UserTestDataHelper.class})
public class UpdateUserEmailAdapterIT {

    @Autowired
    private UpdateUserEmailAdapter adapter;

    @Autowired
    private UserTestDataHelper helper;

    @Test
    void shouldUpdateUserEmail() {
        // Arrange
        UserEntity userEntity = helper.persistDefaultUser();

        User user = new User(userEntity.getId(), new Name(userEntity.getName()),
                new Email(userEntity.getEmail()), new Password(userEntity.getPassword()));

        String newEmail = "test@test.com";
        User updatedUser = user.changeEmailTo(newEmail);

        // Act
        adapter.update(updatedUser);

        // Assert
        assertEquals(newEmail, updatedUser.getEmail().value());
    }
}
