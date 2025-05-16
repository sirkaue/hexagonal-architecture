package com.sirkaue.hexagonalarchitecture.unit.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UserExistsByEmailPort;
import com.sirkaue.hexagonalarchitecture.application.usecase.UpdateUserEmailUseCaseImpl;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        User user = new User(1L, "John Doe", new Email("teste@teste.com"), new Password("123456"));
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
}
