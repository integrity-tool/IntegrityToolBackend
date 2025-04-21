package com.IntegrityTool.Repositories.EdiFileRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.IntegrityTool.Repositories.Interface.IEdiFile;
import com.IntegrityTool.model.EdiFile.EdiFile;
import com.IntegrityTool.service.util.ConnectionManager;
import com.IntegrityTool.service.util.SqlQueryLoader;

@Repository
public class EdiFileRepository implements IEdiFile {
    private final ConnectionManager connectionManager;
    private final SqlQueryLoader sqlQueryLoader;

    @Autowired
    public EdiFileRepository(ConnectionManager connectionManager, SqlQueryLoader sqlQueryLoader) {
        this.connectionManager = connectionManager;
        this.sqlQueryLoader = sqlQueryLoader;
    }

    @Override
    public Map<String, Object> storeEdiFileMetaData(EdiFile ediFile) {
        JdbcTemplate jdbcTemplate = this.connectionManager.getConnection().getJdbcTemplate();
        String query = this.sqlQueryLoader.getSqlQuery("insert_edifile_metadata");
        Objects.requireNonNull(query, "SQL query 'insert_edi_file_metadata' not found by SqlQueryLoader");

        Map<String, Object> resultSet = new HashMap<>();
        int result = jdbcTemplate.update(query,ediFile.getFileType(),ediFile.getFileName(),ediFile.getFileSize(),ediFile.getFilePath(),ediFile.getOriginalHash(),ediFile.getBackupFilePath(),ediFile.getIntegrityStatus(),ediFile.getUserId() );
        if (result == -1) {
            resultSet.put("message", "File MetaData has not been inserted");
        }
        resultSet.put("message", "File MetaData Inserted successfully");
        return resultSet;
    }
}
