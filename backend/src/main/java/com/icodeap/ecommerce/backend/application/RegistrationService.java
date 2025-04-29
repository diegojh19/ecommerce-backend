package com.icodeap.ecommerce.backend.application;

import com.icodeap.ecommerce.backend.domain.model.User;
import com.icodeap.ecommerce.backend.domain.port.IUserRepository;
import com.icodeap.ecommerce.backend.infrastructure.adapter.IUserCrudRepository;

public class RegistrationService {
    private final IUserRepository iUserRepository;

    public RegistrationService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public User register(User user){
        return iUserRepository.save(user);

    }

    public boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }
}
