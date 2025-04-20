package com.IntegrityTool.Repositories.Interface;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface IDoctor {
    public Map<String, Object> storeFileData(MultipartFile multipartFile);

    public Map<String,Object> parseEDI(String ediContent); 
}