package com.IntegrityTool.service.util;

import org.springframework.stereotype.Service;


@Service
public class CommonService {

    public CommonService() {
        
    }
    
    public static String convertToSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
