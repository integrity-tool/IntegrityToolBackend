package com.IntegrityTool.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class FileHashService {

    private static final Logger logger = LoggerFactory.getLogger(FileHashService.class);
    private static final String HASH_ALGORITHM = "SHA-256"; // Using SHA-256 as an industry standard

    public byte[] generateHashBytes(Resource fileResource) throws FileHashingException {
        if (fileResource == null || !fileResource.exists()) {
            logger.error("Cannot generate hash for non-existent or null resource.");
            throw new IllegalArgumentException("File resource must exist.");
        }

        try (InputStream is = fileResource.getInputStream()) {
            // Use the stream hashing method
            return generateHashBytes(is, fileResource.getDescription());
        } catch (IOException e) {
            logger.error("Error opening stream for resource '{}' for hashing.", fileResource.getDescription(), e);
            throw new FileHashingException("Error accessing resource for hashing.", e);
        }
    }

    public byte[] generateHashBytes(InputStream is, String resourceDescription) throws FileHashingException {
        if (is == null) {
            throw new IllegalArgumentException("Input stream cannot be null.");
        }
        if (resourceDescription == null || resourceDescription.trim().isEmpty()) {
            resourceDescription = "unknown resource";
        }

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new FileHashingException("Hash algorithm not available.", e);
        }

        byte[] buffer = new byte[8192]; // 8KB buffer size
        int bytesRead;

        try {
            while ((bytesRead = is.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new FileHashingException("Error reading data for hashing.", e);
        }

        byte[] hashedBytes = messageDigest.digest();
        return hashedBytes;
    }

    public String generateHashHex(Resource fileResource) throws FileHashingException {
        byte[] hashedBytes = generateHashBytes(fileResource); // Get the raw bytes

        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        String hashHex = sb.toString();
        logger.debug("Generated {} hash (hex) for '{}'.", HASH_ALGORITHM, fileResource.getDescription());
        return hashHex;
    }

    public static class FileHashingException extends RuntimeException {
        public FileHashingException(String message, Throwable cause) {
            super(message, cause);
        }

        public FileHashingException(String message) {
            super(message);
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}