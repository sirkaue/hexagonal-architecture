package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.domain.exception.UserNotFoundException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserByIdUseCaseImplTest {

    @Mock
    private EntityFinderHelper entityFinderHelper;

    @InjectMocks
    private FindUserByIdUseCaseImpl findUserByIdUseCase;

    @Test
    void shouldFindUserById() {
        // Arrange
        User user = new User(1L, "John Doe", new Email("teste@teste.com"), new Password("123456"));
        when(entityFinderHelper.findUserByIdOrThrow(anyLong())).thenReturn(user);

        // Act
        User u = findUserByIdUseCase.execute(user.getId());

        // Assert
        assertEquals(user.getId(), u.getId());
        verify(entityFinderHelper, times(1)).findUserByIdOrThrow(u.getId());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        User user = new User(1L, "John Doe", new Email("teste@teste.com"), new Password("123456"));
        when(entityFinderHelper.findUserByIdOrThrow(anyLong()))
                .thenThrow(new UserNotFoundException(String.format("User with id %s not found", user.getId())));

        // Act
        Executable executable = () -> findUserByIdUseCase.execute(user.getId());

        // Assert
        assertThrows(UserNotFoundException.class, executable);
        verify(entityFinderHelper, times(1)).findUserByIdOrThrow(user.getId());
    }
}
