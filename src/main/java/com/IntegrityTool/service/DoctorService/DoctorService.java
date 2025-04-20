package com.IntegrityTool.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.IntegrityTool.Repositories.DoctorRepository.DoctorRepository;

@Service
public class DoctorService {
    private final DoctorRepository _doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this._doctorRepository = doctorRepository;
    }

    public void storeFileData(MultipartFile file) {
        this._doctorRepository.storeFileData(file);
    }

    public void parseEDIFile(String ediContent) {
        this._doctorRepository.parseEDI(ediContent);
    }
}
