package com.example.file_share.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    // Directory where files will be uploaded and stored
    @Value("${upload.dir}")
    private String uploadDir;

    // Create the directory if it doesn't exist
    public FileStorageService() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload.", e);
        }
    }

    // Method to store the file
    public void storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        // Resolve the file path and store it
        Path targetLocation = Paths.get(uploadDir).resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), targetLocation);
    }

    // Method to load a file
    public Resource loadFile(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read the file: " + filename);
        }
    }
}
