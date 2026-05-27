package com.IntegrityTool.Repositories.Interface;

import java.util.List;
import java.util.Map;

import com.IntegrityTool.model.Authentication.LoginParam;
import com.IntegrityTool.model.abstractClasses.Person;

public interface IAuthenticationModule {
    public Map<String, Object> registerUser(Person person);

    public List<Map<String,Object>> getAllRoles();

    public Map<String,Object> loginUser(LoginParam loginParam);

    public void checkAuthorization(Person person, String token);

    public void forgetPassword(Person person);

    public void resetPassword(Person person);

    public void generateToken(Person person);

    public Person updateProfile(Person person);
}
