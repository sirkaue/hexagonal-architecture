package com.sirkaue.hexagonalarchitecture.infra.config;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.in.UpdateUserEmailUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserEmailPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UserExistsByEmailPort;
import com.sirkaue.hexagonalarchitecture.application.usecase.UpdateUserEmailUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateUserEmailConfig {

    @Bean
    public UpdateUserEmailUseCase updateUserEmailUseCase(EntityFinderHelper entityFinderHelper, UserExistsByEmailPort userExistsByEmailPort,
                                                         UpdateUserEmailPort updateUserEmailPort) {
        return new UpdateUserEmailUseCaseImpl(entityFinderHelper, userExistsByEmailPort, updateUserEmailPort);
    }
}
