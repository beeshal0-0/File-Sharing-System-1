package com.example.file_share.controller;

import com.example.file_share.models.FileEntity;
import com.example.file_share.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // Upload a file
    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(
            @RequestParam("fileName") String fileName,
            @RequestParam("fileType") String fileType,
            @RequestParam("filePath") String filePath,
            @RequestParam("fileSize") Long fileSize,
            @RequestParam("username") String username) {
        FileEntity file = fileService.uploadFile(fileName, fileType, filePath, fileSize, username);
        return ResponseEntity.ok(file);
    }

    // Get files uploaded by a user
    @GetMapping("/user/{username}")
    public ResponseEntity<List<FileEntity>> getFilesByUser(@PathVariable String username) {
        List<FileEntity> files = fileService.getFilesByUser(username);
        return ResponseEntity.ok(files);
    }

    // Get file details by ID
    @GetMapping("/{fileId}")
    public ResponseEntity<FileEntity> getFileById(@PathVariable Long fileId) {
        FileEntity file = fileService.getFileById(fileId);
        return ResponseEntity.ok(file);
    }

    // Delete a file by ID
    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok("File deleted successfully!");
    }
}