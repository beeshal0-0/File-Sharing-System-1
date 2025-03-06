package com.example.file_share.service;

import com.example.file_share.models.FileEntity;
import com.example.file_share.models.user;
import com.example.file_share.repositories.FileRepository;
import com.example.file_share.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Autowired
    public FileService(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    // Upload a file
    public FileEntity uploadFile(MultipartFile file, String username) throws IOException {
        // Validate file type
        String fileType = file.getContentType();
        if (!fileType.startsWith("image/") && !fileType.startsWith("video/") && !fileType.startsWith("audio/") && !fileType.equals("application/pdf")) {
            throw new IllegalArgumentException("Invalid file type. Only images, videos, music, and PDFs are allowed.");
        }

        // Find the user who is uploading the file
        user uploadedBy = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create a new FileEntity
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(fileType);
        fileEntity.setFilePath("/uploads/" + file.getOriginalFilename());
        fileEntity.setFileSize(file.getSize());
        fileEntity.setUploadedBy(uploadedBy);
        fileEntity.setUploadDate(LocalDateTime.now());

        // Save the file entity to the database
        return fileRepository.save(fileEntity);
    }

    // Other methods remain unchanged...
}