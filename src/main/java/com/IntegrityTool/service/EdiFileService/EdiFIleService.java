package com.IntegrityTool.service.EdiFileService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.IntegrityTool.Repositories.EdiFileRepository.EdiFileRepository;
import com.IntegrityTool.model.EdiFile.EdiFile;
import com.IntegrityTool.service.util.FileHashService;

@Service
public class EdiFIleService {

    private final EdiFileRepository _ediFileRepository;
    private final FileHashService _fileHashService;

    @Autowired
    public EdiFIleService(EdiFileRepository ediFileRepository,FileHashService fileHashService) {
        this._ediFileRepository = ediFileRepository;
        this._fileHashService = fileHashService;
    }

    public Map<String, Object> storeEdiFileMetaData(MultipartFile file,String filePath) throws Exception {
        byte[] originalhash = this._fileHashService.generateHashBytes(file.getResource());
        EdiFile ediFile = new EdiFile(file.getContentType(), file.getOriginalFilename(), file.getSize(),filePath ,originalhash , null, "SAFE", "0f5e8d4b-2ab3-4fb5-852a-6ba6c799f4a7"); 
        return this._ediFileRepository.storeEdiFileMetaData(ediFile);
    }
}
