package com.IntegrityTool.controller.AuthenticationController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IntegrityTool.model.abstractclass.User;
import com.IntegrityTool.service.AuthenticationService.AuthenticationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService =  authenticationService;
    }

    // Endpoint for registering a new user
    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {
        try{
            this.authenticationService.registerUser(user);
        } catch(Exception e) {

        }
    }

}
