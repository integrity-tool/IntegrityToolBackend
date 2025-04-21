package com.IntegrityTool.controller.DoctorController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.IntegrityTool.DTO.ApiResponse;
import com.IntegrityTool.service.DoctorService.DoctorService;
import com.IntegrityTool.service.EdiFileService.EdiFIleService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Value("${spring.fileuploaddirectory.path}")
    private String UPLOAD_DIR;

    private final DoctorService _doctorService;
    private final EdiFIleService _ediFIleService;

    @Autowired
    public DoctorController(DoctorService doctorService, EdiFIleService ediFIleService) {
        this._doctorService = doctorService;
        this._ediFIleService = ediFIleService;
    }

    @PostMapping("/uploadEdiFile")
    public ResponseEntity<ApiResponse<String>> uploadEdiFile(@RequestParam("EDIFiles") List<MultipartFile> files) {
        if (files.isEmpty()) {
            ApiResponse<String> fileNotFoundResponse = ApiResponse.error(HttpStatus.NO_CONTENT.value(),"No files found", null);
            return new ResponseEntity<>(fileNotFoundResponse, HttpStatus.NO_CONTENT);
        }
        try {
            StringBuilder fileNames = new StringBuilder("Files uploaded successfully: ");
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.write(filePath, file.getBytes());
                fileNames.append(fileName).append(", ");
                this._ediFIleService.storeEdiFileMetaData(file, filePath.toString());
            }
            ApiResponse<String> fileResponse = ApiResponse.success(HttpStatus.OK.value(), "File uploaded successfully",
                    null);
            return new ResponseEntity<>(fileResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> errorResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getLocalizedMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
