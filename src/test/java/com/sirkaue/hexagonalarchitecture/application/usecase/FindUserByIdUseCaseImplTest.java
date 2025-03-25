package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.ports.out.FindUserByIdPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.UserNotFoundException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
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
class FindUserByIdUseCaseImplTest {

    @Mock
    private FindUserByIdPort findUserByIdPort;

    @InjectMocks
    private FindUserByIdUseCaseImpl findUserByIdUseCase;

    @Test
    void shouldFindUserById() {
        // Arrange
        User user = new User(1L, "John Doe", "teste@teste.com", "123456");
        when(findUserByIdPort.findById(anyLong())).thenReturn(Optional.of(user));

        // Act
        User u = findUserByIdUseCase.execute(user.getId());

        // Assert
        assertEquals(user.getId(), u.getId());
        verify(findUserByIdPort, times(1)).findById(anyLong());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        User user = new User(1L, "John Doe", "teste@teste.com", "123456");
        when(findUserByIdPort.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Executable executable = () -> findUserByIdUseCase.execute(user.getId());

        // Assert
        assertThrows(UserNotFoundException.class, executable);
        verify(findUserByIdPort, times(1)).findById(anyLong());
    }
}
