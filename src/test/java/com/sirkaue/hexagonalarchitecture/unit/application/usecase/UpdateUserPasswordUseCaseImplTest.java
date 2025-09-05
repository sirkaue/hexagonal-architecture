package com.sirkaue.hexagonalarchitecture.unit.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.out.PasswordEncoderPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserPort;
import com.sirkaue.hexagonalarchitecture.application.usecase.UpdateUserPasswordUseCaseImpl;
import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.IncorrectCurrentPasswordException;
import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.PasswordConfirmationException;
import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.SamePasswordException;
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
class UpdateUserPasswordUseCaseImplTest {

    @Mock
    private EntityFinderHelper entityFinderHelper;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @Mock
    private UpdateUserPort updateUserPort;

    @InjectMocks
    private UpdateUserPasswordUseCaseImpl updateUserPasswordUseCase;

    @Test
    void shouldUpdateUserPassword() {
        // Arrange
        User user = new User(1L, new Name("John Doe"), new Email("teste@teste.com"), new Password("123456"));
        String newPassword = "admin123";
        User userWithNewPassword = user.changePasswordTo(newPassword);

        Password currentPassword = new Password("123456");
        Password newPasswordObj = new Password(newPassword);
        Password confirmPassword = new Password(newPassword);

        when(entityFinderHelper.findUserByIdOrThrow(anyLong())).thenReturn(user);
        when(passwordEncoderPort.matches(currentPassword, user.getPassword().value())).thenReturn(true);
        when(passwordEncoderPort.matches(newPasswordObj, user.getPassword().value())).thenReturn(false);
        when(passwordEncoderPort.encode(newPasswordObj)).thenReturn(newPassword);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(updateUserPort.update(userCaptor.capture())).thenReturn(userWithNewPassword);

        // Act
        User updatedUser = updateUserPasswordUseCase.execute(user.getId(), currentPassword, newPasswordObj, confirmPassword);

        // Assert
        User savedUser = userCaptor.getValue();
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(newPassword, savedUser.getPassword().value());

        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(newPassword, updatedUser.getPassword().value());

        verify(entityFinderHelper).findUserByIdOrThrow(user.getId());
        verify(passwordEncoderPort).matches(currentPassword, user.getPassword().value());
        verify(passwordEncoderPort).matches(newPasswordObj, user.getPassword().value());
        verify(passwordEncoderPort).encode(newPasswordObj);
        verify(updateUserPort).update(savedUser);
    }

    @Test
    void shouldThrowExceptionWhenCurrentPasswordIsIncorrect() {
        // Arrange
        User user = new User(1L, new Name("John Doe"), new Email("teste@teste.com"), new Password("123456"));
        Password currentPassword = new Password("wrongPassword");
        Password newPassword = new Password("admin123");
        Password confirmPassword = new Password("admin123");

        when(entityFinderHelper.findUserByIdOrThrow(anyLong())).thenReturn(user);
        when(passwordEncoderPort.matches(currentPassword, user.getPassword().value())).thenReturn(false);

        // Act
        Executable executable = () -> updateUserPasswordUseCase.execute(user.getId(), currentPassword, newPassword, confirmPassword);

        // Assert
        assertThrows(IncorrectCurrentPasswordException.class, executable);
        verify(entityFinderHelper).findUserByIdOrThrow(user.getId());
        verify(passwordEncoderPort).matches(currentPassword, user.getPassword().value());
    }

    @Test
    void shouldThrowExceptionWhenNewPasswordIsSameAsCurrent() {
        // Arrange
        User user = new User(1L, new Name("John Doe"), new Email("teste@teste.com"), new Password("123456"));
        Password currentPassword = new Password("123456");
        Password newPassword = new Password("123456");
        Password confirmPassword = new Password("123456");

        when(entityFinderHelper.findUserByIdOrThrow(anyLong())).thenReturn(user);
        when(passwordEncoderPort.matches(currentPassword, user.getPassword().value())).thenReturn(true);
        when(passwordEncoderPort.matches(newPassword, user.getPassword().value())).thenReturn(true);

        // Act
        Executable executable = () -> updateUserPasswordUseCase.execute(user.getId(), currentPassword, newPassword, confirmPassword);

        // Assert
        assertThrows(SamePasswordException.class, executable);
        verify(entityFinderHelper).findUserByIdOrThrow(user.getId());
        verify(passwordEncoderPort, times(2)).matches(any(Password.class), anyString());
        verifyNoMoreInteractions(passwordEncoderPort, entityFinderHelper, updateUserPort);
    }

    @Test
    void shouldThrowExceptionWhenNewPasswordAndConfirmPasswordDoNotMatch() {
        // Arrange
        User user = new User(1L, new Name("John Doe"), new Email("teste@teste.com"), new Password("123456"));

        Password currentPassword = new Password("123456");
        Password newPassword = new Password("admin123");
        Password confirmPassword = new Password("admin1234");

        when(entityFinderHelper.findUserByIdOrThrow(anyLong())).thenReturn(user);
        when(passwordEncoderPort.matches(currentPassword, user.getPassword().value())).thenReturn(true);
        when(passwordEncoderPort.matches(newPassword, user.getPassword().value())).thenReturn(false);

        // Act
        Executable executable = () -> updateUserPasswordUseCase.execute(user.getId(), currentPassword, newPassword, confirmPassword);

        // Assert
        assertThrows(PasswordConfirmationException.class, executable);
        verify(entityFinderHelper).findUserByIdOrThrow(user.getId());
        verify(passwordEncoderPort).matches(currentPassword, user.getPassword().value());
        verify(passwordEncoderPort).matches(newPassword, user.getPassword().value());
        verifyNoMoreInteractions(passwordEncoderPort, entityFinderHelper, updateUserPort);
    }
}
