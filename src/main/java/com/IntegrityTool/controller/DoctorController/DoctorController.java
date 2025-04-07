package com.IntegrityTool.controller.DoctorController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.IntegrityTool.DTO.ApiResponse;
import com.IntegrityTool.model.abstractClasses.Address;
import com.IntegrityTool.model.abstractClasses.User;

@RestController
@RequestMapping("/doctor")
public class DoctorController extends User {

    public DoctorController(String firstName, String lastName, String email, String gender, Date dateOfBirth,String password, boolean isActive, Address address) {
        super(firstName, lastName, email, gender, dateOfBirth, password, isActive, address);
    }
    @PostMapping("/uploadEdiFile")
    public ResponseEntity<ApiResponse<String>> uploadEdiFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
           String status = "file is empty";
       }

        try {
            // Path uploadPath = Paths.get(UPLOAD_DIR);

        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}
