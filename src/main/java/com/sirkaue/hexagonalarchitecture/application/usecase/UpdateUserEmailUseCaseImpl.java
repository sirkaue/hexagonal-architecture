package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.in.UpdateUserEmailUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UserExistsByEmailPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.EmailAlreadyExistsException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateUserEmailUseCaseImpl implements UpdateUserEmailUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserEmailUseCaseImpl.class);

    private final EntityFinderHelper entityFinderHelper;
    private final UserExistsByEmailPort userExistsByEmailPort;
    private final UpdateUserPort updateUserPort;

    public UpdateUserEmailUseCaseImpl(EntityFinderHelper entityFinderHelper, UserExistsByEmailPort userExistsByEmailPort, UpdateUserPort updateUserPort) {
        this.entityFinderHelper = entityFinderHelper;
        this.userExistsByEmailPort = userExistsByEmailPort;
        this.updateUserPort = updateUserPort;
    }


    @Override
    public User execute(Long id, String newEmail) {
        log.info("Finding user with id: {}", id);
        User user = entityFinderHelper.findUserByIdOrThrow(id);

        log.info("Validating email");
        validateEmail(new Email(newEmail), user);

        log.info("Updating email for user with id: {}", id);
        User updatedUser = user.changeEmailTo(newEmail);
        return updateUserPort.update(updatedUser);
    }

    private void validateEmail(Email newEmail, User user) {
        if (user.getEmail().equals(newEmail)) {
            throw new EmailAlreadyExistsException("The new email is the same as the current one");
        }

        if (userExistsByEmailPort.existsByEmail(newEmail.value())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }
}
