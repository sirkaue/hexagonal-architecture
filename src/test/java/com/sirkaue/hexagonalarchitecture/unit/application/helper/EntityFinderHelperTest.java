package com.sirkaue.hexagonalarchitecture.unit.application.helper;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.out.FindUserByIdPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.UserNotFoundException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Name;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntityFinderHelperTest {

    @Mock
    private FindUserByIdPort findUserByIdPort;

    @InjectMocks
    private EntityFinderHelper entityFinderHelper;

    @Test
    void shouldFindUserById() {
        // Arrange
        User user = new User(1L, new Name("John Doe"), new Email("teste@teste.com"), new Password("password"));
        when(findUserByIdPort.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        User result = entityFinderHelper.findUserByIdOrThrow(user.getId());

        // Assert
        assertEquals(user.getId(), result.getId());
        verify(findUserByIdPort, times(1)).findById(user.getId());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        Long userId = 1L;
        when(findUserByIdPort.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Executable executable = () -> entityFinderHelper.findUserByIdOrThrow(userId);

        // Assert
        assertThrows(UserNotFoundException.class, executable);
        verify(findUserByIdPort, times(1)).findById(userId);
    }
}