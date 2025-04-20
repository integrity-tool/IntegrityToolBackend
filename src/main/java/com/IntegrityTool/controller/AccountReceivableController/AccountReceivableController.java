package com.IntegrityTool.controller.AccountReceivableController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IntegrityTool.DTO.ApiResponse;
import com.IntegrityTool.model.EdiFile.EdiFileParam;
import com.IntegrityTool.model.EdiFile.EdiTask;
import com.IntegrityTool.service.AccountReceivableService.AccountReceivableService;
import com.IntegrityTool.service.util.CMS1500PdfGenerator;
import com.IntegrityTool.service.util.EdiParserService;

@RestController
@RequestMapping("/accountReceivable")

public class AccountReceivableController {

    @Value("${spring.fileuploaddirectory.path}")
    private String UPLOAD_DIR;

    private final AccountReceivableService _accountReceivableService;
    private final EdiParserService _ediParserService;

    @Autowired
    public AccountReceivableController(
        AccountReceivableService accountReceivableService,
        EdiParserService ediParserService
    ) {
        this._accountReceivableService = accountReceivableService;
        this._ediParserService = ediParserService;
    }

    @GetMapping("/getAllFiles")
    public ResponseEntity<ApiResponse<String>> getFilesInFolder() {
        String customPath = "";
        try {
            String directoryPath = UPLOAD_DIR;
            if (customPath != null && !customPath.isEmpty()) {
                directoryPath = customPath;
            }

            List<String> getAllFiles = this._accountReceivableService.getAllFiles(directoryPath);
            ApiResponse<List<String>> allFiles = ApiResponse.success(HttpStatus.OK.value(),
                    "Files fetched successfully", getAllFiles);
            return new ResponseEntity(allFiles, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> errorResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getLocalizedMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/parseEDIFile")
    public ResponseEntity<ApiResponse<String>> parseEDIFIle(@RequestBody EdiFileParam ediFileParam) {
        try {
            String fileNameWithExtension = ediFileParam.getFileName().concat(".edi");
            ediFileParam.setFileName(fileNameWithExtension);
            Map<String, Object> parseEdiFile = this._accountReceivableService.parseEDIFile(ediFileParam, UPLOAD_DIR);
            EdiTask ediTask = (EdiTask) parseEdiFile.get("ediFileMetaData");
            Map<String, Object> parsedFileMetaData = processFileQueue(ediTask);
            List<Map<String,Object>> genereatedPdf = CMS1500PdfGenerator.generateClaimForm(parsedFileMetaData, ediFileParam.getFileName());

            ApiResponse<List<Map<String,Object>>> fileResponse = ApiResponse.success(HttpStatus.OK.value(),"File fetched successfully", genereatedPdf);
            return new ResponseEntity(fileResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> errorResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getLocalizedMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<String> parseSegments(byte[] ediPayload) throws IOException {
        List<String> segments = new ArrayList<>();
        String ediContent = new String(ediPayload, StandardCharsets.UTF_8).trim();

        String[] rawSegments = ediContent.split("~");

        for (String segment : rawSegments) {
            String trimmedSegment = segment.trim();
            if (!trimmedSegment.isEmpty()) {
                segments.add(trimmedSegment);
            }
        }
        return segments;
    }

    private Map<String, Object> processFileQueue(EdiTask ediTask) {
        try {
            byte[] fileMetaData = ediTask.getPayload();
            List<String> segements = this.parseSegments(fileMetaData);
            return this._ediParserService.parseEdiData(segements);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
}
