package com.IntegrityTool.controller.AuthenticationController;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.IntegrityTool.DTO.ApiResponse;
import com.IntegrityTool.model.Authentication.LoginParam;
import com.IntegrityTool.model.abstractClasses.User;
import com.IntegrityTool.service.AuthenticationService.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService _authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this._authenticationService = authenticationService;
    }

    // Endpoint for registering a new user
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody User user) {
        try {
            Map<String, Object> userInfo = this._authenticationService.registerUser(user);
            List<Map<String, Object>> fetchInfo = (List<Map<String, Object>>) userInfo.get("#result-set-1");

            Map<String, Object> row = (fetchInfo != null && !fetchInfo.isEmpty()) ? fetchInfo.get(0) : new HashMap<>();
            String status = row.getOrDefault("Status", "").toString();
            String emailid = row.getOrDefault("emailid", "").toString();

            ApiResponse<String> registerResponse = ApiResponse.success(HttpStatus.OK.value(),status, emailid);
            return new ResponseEntity<>(registerResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> errorResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getLocalizedMessage(),null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody LoginParam loginParam) {
        try {
            Map<String,Object> resultSet = this._authenticationService.loginUser(loginParam);
            List<Map<String, Object>> fetchInfo = (List<Map<String, Object>>) resultSet.get("#result-set-1");

            Map<String, Object> row = (fetchInfo != null && !fetchInfo.isEmpty()) ? fetchInfo.get(0) : new HashMap<>();
            String status = row.getOrDefault("Status", "").toString();
            String emailid = row.getOrDefault("emailid", "").toString();

            int httpStatus = -1;
            if(String.valueOf(status).equals("Invalid email or password!")) 
                httpStatus = HttpStatus.UNAUTHORIZED.value();
                
            ApiResponse<String> registerResponse = ApiResponse.success(httpStatus,status, emailid);
            return new ResponseEntity<>(registerResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> errorResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getLocalizedMessage(),null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void createUserSession() {
        try {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
