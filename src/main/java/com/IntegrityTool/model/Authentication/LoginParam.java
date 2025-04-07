package com.IntegrityTool.model.Authentication;

public class LoginParam {
    private String emailId;
    private String password;

    public LoginParam() {
        this.emailId = "";
        this.password = "";
    }

    public String getEmailId() {
        return this.emailId;
    }

    public String getPassword() {
        return this.password;
    }
}
