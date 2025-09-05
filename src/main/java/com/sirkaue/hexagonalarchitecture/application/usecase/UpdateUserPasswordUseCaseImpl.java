package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.in.UpdateUserPasswordUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.PasswordEncoderPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.IncorrectCurrentPasswordException;
import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.PasswordConfirmationException;
import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.SamePasswordException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UpdateUserPasswordUseCaseImpl implements UpdateUserPasswordUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserPasswordUseCaseImpl.class);

    private final EntityFinderHelper entityFinderHelper;
    private final PasswordEncoderPort passwordEncoderPort;
    private final UpdateUserPort updateUserPort;

    public UpdateUserPasswordUseCaseImpl(EntityFinderHelper entityFinderHelper, PasswordEncoderPort passwordEncoderPort,
                                         UpdateUserPort updateUserPort) {
        this.entityFinderHelper = entityFinderHelper;
        this.passwordEncoderPort = passwordEncoderPort;
        this.updateUserPort = updateUserPort;
    }

    @Override
    public User execute(Long id, Password currentPassword, Password newPassword, Password confirmPassword) {
        log.info("Finding user with id: {}", id);
        User user = entityFinderHelper.findUserByIdOrThrow(id);

        log.info("Validating password");
        validatePassword(user.getPassword(), currentPassword, newPassword, confirmPassword);

        log.info("Updating password for user with id: {}", id);
        String hashedNewPassword = passwordEncoderPort.encode(newPassword);
        User updatedUser = user.changePasswordTo(hashedNewPassword);

        return updateUserPort.update(updatedUser);
    }

    private void validatePassword(Password currentPassword, Password currentPasswordProvided,
                                  Password newPassword, Password confirmPassword) {
        validateCurrentPassword(currentPassword, currentPasswordProvided);
        validateNewPasswordIsDifferent(currentPassword, newPassword);
        validatePasswordConfirmation(newPassword, confirmPassword);
    }

    private void validateCurrentPassword(Password currentPassword, Password currentPasswordProvided) {
        log.info("Validating current password");
        if (!passwordEncoderPort.matches(currentPasswordProvided, currentPassword.value())) {
            throw new IncorrectCurrentPasswordException();
        }
    }

    private void validateNewPasswordIsDifferent(Password currentPassword, Password newPassword) {
        log.info("Validating new password");
        if (passwordEncoderPort.matches(newPassword, currentPassword.value())) {
            throw new SamePasswordException();
        }
    }

    private void validatePasswordConfirmation(Password newPassword, Password confirmPassword) {
        log.info("Validating password confirmation");
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordConfirmationException();
        }
    }
}
