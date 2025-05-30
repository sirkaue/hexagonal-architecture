package com.sirkaue.hexagonalarchitecture.integration.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.FindUserByIdAdapter;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper.impl.UserEntityMapperImpl;
import com.sirkaue.hexagonalarchitecture.integration.infra.adapters.support.helper.UserTestDataHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DataJpaTest
@Import({FindUserByIdAdapter.class, UserEntityMapperImpl.class, UserTestDataHelper.class})
class FindUserByIdAdapterIT {

    @Autowired
    private FindUserByIdAdapter adapter;

    @Autowired
    private UserTestDataHelper helper;

    @Test
    void shouldFindUserByIdWhenExists() {
        // Arrange
        UserEntity userEntity = helper.persistDefaultUser();

        // Act
        Optional<User> result = adapter.findById(userEntity.getId());

        // Assert
        assertTrue(result.isPresent(), "User should be found");
        User user = result.get();
        assertEquals(userEntity.getId(), user.getId(), "IDs devem ser iguais");
        assertEquals(userEntity.getName(), user.getName(), "Nomes devem ser iguais");
        assertEquals(userEntity.getEmail(), user.getEmail().value(), "Emails devem ser iguais");
        assertEquals(userEntity.getPassword(), user.getPassword().value(), "Senhas devem ser iguais");
    }

    @Test
    void shouldReturnEmptyWhenUserDoesNotExist() {
        // Act
        Optional<User> result = adapter.findById(999L);

        // Assert
        assertTrue(result.isEmpty(), "Não deve encontrar usuário com ID inválido");
    }
}
