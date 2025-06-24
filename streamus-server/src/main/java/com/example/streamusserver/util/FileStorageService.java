package com.example.streamusserver.util;

import com.example.streamusserver.post.model.enums.ImageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    @Value("${file.path}")
    private String uploadDir;
    public String uploadFile(MultipartFile file, Long  accountId) {
        if (file == null || file.isEmpty()) {
            return "";
        }

        try {
            // Ensure directory exists
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            // Generate unique filename
            String fileName = System.currentTimeMillis() + "_" + accountId + "_" + file.getOriginalFilename();
            Path targetLocation = uploadPath.resolve(fileName);

            // Copy file to target location
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename(), ex);
        }

    }
}
