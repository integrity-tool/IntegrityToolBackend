package com.IntegrityTool.Repositories.Interface;

import java.util.Map;

import com.IntegrityTool.model.EdiFile.EdiFile;

public interface IEdiFile {
    public Map<String,Object> storeEdiFileMetaData(EdiFile ediFile);
}
