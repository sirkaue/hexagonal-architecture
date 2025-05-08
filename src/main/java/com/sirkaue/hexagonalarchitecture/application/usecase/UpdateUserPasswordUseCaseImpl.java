package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.in.UpdateUserPasswordUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.PasswordEncoderPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserPasswordPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.IncorrectCurrentPasswordException;
import com.sirkaue.hexagonalarchitecture.domain.exception.PasswordConfirmationException;
import com.sirkaue.hexagonalarchitecture.domain.exception.SamePasswordException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateUserPasswordUseCaseImpl implements UpdateUserPasswordUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserPasswordUseCaseImpl.class);

    private final EntityFinderHelper entityFinderHelper;
    private final PasswordEncoderPort passwordEncoderPort;
    private final UpdateUserPasswordPort updateUserPasswordPort;

    public UpdateUserPasswordUseCaseImpl(EntityFinderHelper entityFinderHelper, PasswordEncoderPort passwordEncoderPort,
                                         UpdateUserPasswordPort updateUserPasswordPort) {
        this.entityFinderHelper = entityFinderHelper;
        this.passwordEncoderPort = passwordEncoderPort;
        this.updateUserPasswordPort = updateUserPasswordPort;
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

        return updateUserPasswordPort.update(updatedUser);
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
            throw new IncorrectCurrentPasswordException("The current password is incorrect");
        }
    }

    private void validateNewPasswordIsDifferent(Password currentPassword, Password newPassword) {
        log.info("Validating new password");
        if (passwordEncoderPort.matches(newPassword, currentPassword.value())) {
            throw new SamePasswordException("The new password is the same as the current one");
        }
    }

    private void validatePasswordConfirmation(Password newPassword, Password confirmPassword) {
        log.info("Validating password confirmation");
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordConfirmationException("Password confirmation does not match");
        }
    }
}
