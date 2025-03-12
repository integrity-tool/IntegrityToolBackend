package com.IntegrityTool.Repositories.Interface.IAuthentication;

import com.IntegrityTool.model.abstractclass.User;

public interface IAuthentication {
    public void registerUser(User user);    
    public void loginUser(User user);
    public void checkAuthorization(User user,String token);
    public void forgetPassword(User user);
    public void resetPassword(User user);
    public void generateToken(User user);
    public User updateProfile(User user);
}
