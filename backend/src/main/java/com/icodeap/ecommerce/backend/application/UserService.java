package com.icodeap.ecommerce.backend.application;

import com.icodeap.ecommerce.backend.domain.model.User;
import com.icodeap.ecommerce.backend.domain.port.IUserRepository;

import java.util.Optional;

public class UserService {

    private final IUserRepository iUserRepository;

    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public User save(User user){
        return iUserRepository.save(user);
    }
    public User findById(Integer id){
        return iUserRepository.findById(id);
    }
    public User finByEmail(String email){
        return iUserRepository.findByEmail(email);
    }
    public  boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }
    public User findByTokenPassword(String tokenPassword){
        return iUserRepository.findByTokenPassword(tokenPassword);
    }

    public boolean verificarUsuario(String token){
        return iUserRepository.verificarUsuario(token);
    }



}
