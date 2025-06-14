package com.sirkaue.hexagonalarchitecture.unit.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UserExistsByEmailPort;
import com.sirkaue.hexagonalarchitecture.application.usecase.UpdateUserEmailUseCaseImpl;
import com.sirkaue.hexagonalarchitecture.domain.exception.EmailAlreadyExistsException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Name;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserEmailUseCaseImplTest {

    @Mock
    private EntityFinderHelper entityFinderHelper;

    @Mock
    private UserExistsByEmailPort userExistsByEmailPort;

    @Mock
    private UpdateUserPort updateUserPort;

    @InjectMocks
    private UpdateUserEmailUseCaseImpl updateUserEmailUseCase;

    @Test
    void shouldUpdateUserEmail() {
        // Arrange
        User user = new User(1L, new Name("John Doe"), new Email("teste@teste.com"), new Password("123456"));
        String newEmail = "admin@admin.com";
        User userWithNewEmail = user.changeEmailTo(newEmail);

        when(entityFinderHelper.findUserByIdOrThrow(anyLong())).thenReturn(user);
        when(userExistsByEmailPort.existsByEmail(newEmail)).thenReturn(false);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(updateUserPort.update(userCaptor.capture())).thenReturn(userWithNewEmail);

        // Act
        User updatedUser = updateUserEmailUseCase.execute(user.getId(), newEmail);

        // Assert
        User savedUser = userCaptor.getValue();
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(newEmail, savedUser.getEmail().value());

        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(newEmail, updatedUser.getEmail().value());

        verify(entityFinderHelper).findUserByIdOrThrow(user.getId());
        verify(userExistsByEmailPort).existsByEmail(newEmail);
        verify(updateUserPort).update(savedUser);
    }

    @Test
    void shouldNotUpdateAndThrowEmailAlreadyExistsExceptionWhenEmailIsTheSame() {
        // Arrange
        final String SAME_EMAIL_EXCEPTION = "The new email is the same as the current one";
        User user = new User(1L, new Name("John Doe"), new Email("teste@teste.com"), new Password("123456"));
        String sameEmail = "teste@teste.com";

        when(entityFinderHelper.findUserByIdOrThrow(anyLong())).thenReturn(user);

        // Act
        Executable executable = () -> updateUserEmailUseCase.execute(user.getId(), sameEmail);

        // Assert
        var ex = assertThrows(EmailAlreadyExistsException.class, executable);
        assertEquals(SAME_EMAIL_EXCEPTION, ex.getMessage());

        verify(entityFinderHelper).findUserByIdOrThrow(user.getId());
        verify(userExistsByEmailPort, never()).existsByEmail(sameEmail);
        verify(updateUserPort, never()).update(user);
    }

    @Test
    void shouldNotUpdateAndThrowEmailAlreadyExistsExceptionWhenEmailExists() {
        // Arrange
        final String EMAIL_ALREADY_EXISTS_EXCEPTION = "Email already exists";
        User user = new User(1L, new Name("John Doe"), new Email("teste@teste.com"), new Password("123456"));
        String newEmail = "admin@admin.com";

        when(entityFinderHelper.findUserByIdOrThrow(anyLong())).thenReturn(user);
        when(userExistsByEmailPort.existsByEmail(newEmail)).thenReturn(true);

        // Act
        Executable executable = () -> updateUserEmailUseCase.execute(user.getId(), newEmail);

        // Assert
        var ex = assertThrows(EmailAlreadyExistsException.class, executable);
        assertEquals(EMAIL_ALREADY_EXISTS_EXCEPTION, ex.getMessage());

        verify(entityFinderHelper).findUserByIdOrThrow(user.getId());
        verify(userExistsByEmailPort).existsByEmail(newEmail);
        verify(updateUserPort, never()).update(user);
    }
}
