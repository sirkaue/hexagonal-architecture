package com.sirkaue.hexagonalarchitecture.infra.config;

import com.sirkaue.hexagonalarchitecture.application.ports.in.InsertUserUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.InsertUserPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UserExistsByEmailPort;
import com.sirkaue.hexagonalarchitecture.application.usecase.InsertUserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertUserConfig {

    @Bean
    public InsertUserUseCase insertUserUseCase(InsertUserPort insertUserPort, UserExistsByEmailPort userExistsByEmailPort) {
        return new InsertUserUseCaseImpl(insertUserPort, userExistsByEmailPort);
    }
}
