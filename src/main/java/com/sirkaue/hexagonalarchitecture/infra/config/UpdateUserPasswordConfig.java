package com.sirkaue.hexagonalarchitecture.infra.config;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.in.UpdateUserPasswordUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.PasswordEncoderPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserPasswordPort;
import com.sirkaue.hexagonalarchitecture.application.usecase.UpdateUserPasswordUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateUserPasswordConfig {

    @Bean
    public UpdateUserPasswordUseCase updateUserPasswordUseCase(EntityFinderHelper entityFinderHelper, PasswordEncoderPort passwordEncoderPort,
                                                               UpdateUserPasswordPort updateUserPasswordPort) {
        return new UpdateUserPasswordUseCaseImpl(entityFinderHelper, passwordEncoderPort, updateUserPasswordPort);
    }
}
