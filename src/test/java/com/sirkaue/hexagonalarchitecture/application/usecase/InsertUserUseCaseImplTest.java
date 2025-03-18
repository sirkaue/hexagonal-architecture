package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.ports.out.InsertUserPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UserExistsByEmailPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.EmailAlreadyExistsException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
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
class InsertUserUseCaseImplTest {

    @Mock
    private InsertUserPort insertUserPort;

    @Mock
    private UserExistsByEmailPort userExistsByEmailPort;

    @InjectMocks
    private InsertUserUseCaseImpl insertUserUseCase;

    @Test
    void shouldInsertUserWhenEmailDoesNotExist() {
        // Arrange
        User user = new User(1L, "John Doe", "teste@teste.com", "123456");
        when(userExistsByEmailPort.existsByEmail(anyString())).thenReturn(false);

        // Act
        insertUserUseCase.execute(user);

        // Assert
        verify(userExistsByEmailPort, times(1)).existsByEmail(anyString());
        verify(insertUserPort, times(1)).insert(any(User.class));
    }

    @Test
    void shouldThrowEmailAlreadyExistsExceptionWhenEmailExists() {
        // Arrange
        final String EMAIL_ALREADY_EXISTS = "Email already exists";
        User user = new User(1L, "John Doe", "teste@teste.com", "123456");
        when(userExistsByEmailPort.existsByEmail(anyString())).thenReturn(true);

        // Act
        Executable executable = () -> insertUserUseCase.execute(user);

        // Assert
        var ex = assertThrows(EmailAlreadyExistsException.class, executable);
        assertEquals(EMAIL_ALREADY_EXISTS, ex.getMessage());

        verify(userExistsByEmailPort, times(1)).existsByEmail(anyString());
        verify(insertUserPort, never()).insert(any(User.class));
    }
}