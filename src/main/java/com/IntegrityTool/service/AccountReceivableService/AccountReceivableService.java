package com.IntegrityTool.service.AccountReceivableService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.IntegrityTool.Repositories.AccountReceivableRepository.AccountReceivableRepository;
import com.IntegrityTool.model.EdiFile.EdiFileParam;

@Service
public class AccountReceivableService {
    private final AccountReceivableRepository _accountReceivableRepository; 

    @Autowired
    public AccountReceivableService(AccountReceivableRepository accountReceivableRepository) {
        this._accountReceivableRepository = accountReceivableRepository;
    }

    public Map<String,Object> parseEDIFile(EdiFileParam ediFileParam,String uploadDirectoryPath) throws IOException {
       return this._accountReceivableRepository.parseEDIFIle(ediFileParam,uploadDirectoryPath);
    }

    public List<String> getAllFiles(String directoryPath) {
        return this._accountReceivableRepository.getAllFiles(directoryPath);
    }

}
