package com.IntegrityTool.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IntegrityTool.Repositories.AuthenticationRespository.AuthenticationRepository;
import com.IntegrityTool.model.abstractclass.User;

@Service
public class AuthenticationService {

    private final AuthenticationRepository _authenticationRepository;

    @Autowired
    public AuthenticationService(AuthenticationRepository authenticationRepository) {
        this._authenticationRepository = authenticationRepository;
    }

    public void registerUser(User user) {
        this._authenticationRepository.registerUser(user);
    }
}
