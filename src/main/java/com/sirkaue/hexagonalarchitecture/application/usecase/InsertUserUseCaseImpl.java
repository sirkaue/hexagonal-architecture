package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.ports.in.InsertUserUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.InsertUserPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.PasswordEncoderPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UserExistsByEmailPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.EmailAlreadyExistsException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InsertUserUseCaseImpl implements InsertUserUseCase {

    private static final Logger log = LoggerFactory.getLogger(InsertUserUseCaseImpl.class);

    private final InsertUserPort insertUserPort;
    private final UserExistsByEmailPort userExistsByEmailPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public InsertUserUseCaseImpl(InsertUserPort insertUserPort, UserExistsByEmailPort userExistsByEmailPort,
                                 PasswordEncoderPort passwordEncoderPort) {
        this.insertUserPort = insertUserPort;
        this.userExistsByEmailPort = userExistsByEmailPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public User execute(User user) {
        log.info("Creating user {} with email: {}", user.getName(), user.getEmail().value());

        if (userExistsByEmailPort.existsByEmail(user.getEmail().value())) {
            throw new EmailAlreadyExistsException();
        }

        log.info("Encoding password");
        String hashedPassword = passwordEncoderPort.encode(user.getPassword());
        user = user.changePasswordTo(hashedPassword);
        return insertUserPort.insert(user);
    }
}
