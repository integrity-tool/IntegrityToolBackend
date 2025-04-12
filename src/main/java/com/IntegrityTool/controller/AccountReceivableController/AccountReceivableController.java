package com.IntegrityTool.controller.AccountReceivableController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IntegrityTool.service.AuthenticationService.AuthenticationService;

@RestController
@RequestMapping("/accountReceivable")

public class AccountReceivableController {
    
    private final AuthenticationService _authenticationService;

    public AccountReceivableController(AuthenticationService authenticationService) { 
        this._authenticationService = authenticationService;
    }
}
