package com.icodeap.ecommerce.backend.domain.port;

import com.icodeap.ecommerce.backend.domain.model.User;

import java.util.Optional;

public interface IUserRepository {

    User save(User user);
    User findByEmail(String email);
    User findByTokenPassword(String tokenPassword);
    User findById(Integer id);
    boolean existsByEmail(String email);
    boolean verificarUsuario(String token);








}
