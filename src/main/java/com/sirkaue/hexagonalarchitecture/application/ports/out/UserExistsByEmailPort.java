package com.sirkaue.hexagonalarchitecture.application.ports.out;

public interface UserExistsByEmailPort {

    boolean existsByEmail(String email);
}
