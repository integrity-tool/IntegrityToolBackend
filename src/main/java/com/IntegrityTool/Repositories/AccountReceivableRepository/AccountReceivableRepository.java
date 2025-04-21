package com.IntegrityTool.Repositories.AccountReceivableRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.IntegrityTool.Repositories.Interface.IAccountReceivable;
import com.IntegrityTool.model.EdiFile.EdiFileParam;
import com.IntegrityTool.model.EdiFile.EdiTask;
import com.IntegrityTool.service.util.ConnectionManager;
import com.IntegrityTool.service.util.SqlQueryLoader;

@Repository
public class AccountReceivableRepository implements IAccountReceivable {
    @Value("${spring.segement.terminator}")
    private String SEGEMENT_SEPERATOR;

    @Value("${spring.element.seperator}")
    private String ELEMENT_SEPERATOR;

    private final ConnectionManager connectionManager;
    private final SqlQueryLoader sqlQueryLoader;

    @Autowired
    public AccountReceivableRepository(ConnectionManager connectionManager, SqlQueryLoader sqlQueryLoader) {
        this.connectionManager = connectionManager;
        this.sqlQueryLoader = sqlQueryLoader;
    }

    public Map<String, Object> parseEDIFIle(EdiFileParam ediFileParam, String uploadDirectoryPath) throws IOException {
        Path filePath = Paths.get(uploadDirectoryPath).resolve(ediFileParam.getFileName()).normalize();

        if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
            return null;
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        byte[] data = Files.readAllBytes(filePath);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(
                ContentDisposition.attachment().filename(filePath.getFileName().toString()).build());

        EdiTask ediTask = new EdiTask(data, ediFileParam.getFileName());

        Map<String, Object> resultSet = new HashMap<>();
        resultSet.put("ediFileMetaData", ediTask);
        return resultSet;
    }

    public List<String> getAllFiles(String directoryPath) {
        List<String> fileNames = null;
        File folder = new File(directoryPath);
        File[] files = folder.listFiles();

        if (files != null) {
            fileNames = Arrays.stream(files)
                    .filter(File::isFile)
                    .map(File::getName)
                    .collect(Collectors.toList());
        }
        return fileNames;
    }

    @Override
    public String getOriginalHashByFileName(String fileNameWithExtension) {
        JdbcTemplate jdbcTemplate = this.connectionManager.getConnection().getJdbcTemplate();
        String query = this.sqlQueryLoader.getSqlQuery("fetch_original_hash");
        Objects.requireNonNull(query, "SQL query 'insert_edi_file_metadata' not found by SqlQueryLoader");

        String originalHash = jdbcTemplate.queryForObject(query, String.class, fileNameWithExtension);
        if (originalHash == "") {
            return "";
        }
        return originalHash;
    }
}
