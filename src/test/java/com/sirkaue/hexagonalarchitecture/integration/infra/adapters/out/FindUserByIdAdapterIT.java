package com.sirkaue.hexagonalarchitecture.integration.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.FindUserByIdAdapter;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper.impl.UserEntityMapperImpl;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.UserJpaRepository;
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
@Import({FindUserByIdAdapter.class, UserEntityMapperImpl.class})
class FindUserByIdAdapterIT {

    @Autowired
    private FindUserByIdAdapter findUserByIdAdapter;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void shouldFindUserByIdWhenExists() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setName("John Doe");
        userEntity.setEmail("john@example.com");
        userEntity.setPassword("password123");
        UserEntity savedUser = userJpaRepository.save(userEntity);

        // Act
        Optional<User> result = findUserByIdAdapter.findById(savedUser.getId());

        // Assert
        assertTrue(result.isPresent(), "User should be found");
        User user = result.get();
        assertEquals(savedUser.getId(), user.getId(), "IDs devem ser iguais");
        assertEquals(savedUser.getName(), user.getName(), "Nomes devem ser iguais");
        assertEquals(savedUser.getEmail(), user.getEmail().value(), "Emails devem ser iguais");
        assertEquals(savedUser.getPassword(), user.getPassword().value(), "Senhas devem ser iguais");
    }
}
