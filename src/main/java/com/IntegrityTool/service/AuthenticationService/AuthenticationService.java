package com.IntegrityTool.service.AuthenticationService;

import com.IntegrityTool.Repositories.AuthenticationRepository.AuthenticationRepository;
import com.IntegrityTool.model.Authentication.LoginParam;
import com.IntegrityTool.model.abstractClasses.Person;
import com.IntegrityTool.model.abstractClasses.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationRepository _authenticationRepository;

    @Autowired
    public AuthenticationService(AuthenticationRepository authenticationRepository) {
        this._authenticationRepository = authenticationRepository;
    }

    public Map<String, Object> registerUser(User user) {
        Person person = user;
        person.setPersonId();
        return this._authenticationRepository.registerUser(person);
    }

    public Map<String, Object> loginUser(LoginParam loginParam) {
        return this._authenticationRepository.loginUser(loginParam);
    }
}
