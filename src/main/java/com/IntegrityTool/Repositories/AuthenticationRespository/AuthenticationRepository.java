package com.IntegrityTool.Repositories.AuthenticationRespository;

import org.springframework.stereotype.Repository;

import com.IntegrityTool.Repositories.Interface.IAuthentication.IAuthentication;
import com.IntegrityTool.model.abstractclass.User;

@Repository
public class AuthenticationRepository implements IAuthentication {

    public AuthenticationRepository() {

    }

    @Override
    public void registerUser(User user) {
       
    }

    @Override
    public void loginUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loginUser'");
    }

    @Override
    public void checkAuthorization(User user, String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkAuthorization'");
    }

    @Override
    public void forgetPassword(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'forgetPassword'");
    }

    @Override
    public void resetPassword(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetPassword'");
    }

    @Override
    public void generateToken(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
    }

    @Override
    public User updateProfile(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProfile'");
    }
}
