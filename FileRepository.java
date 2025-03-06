package com.example.file_share.repositories;

import com.example.file_share.models.FileEntity;
import com.example.file_share.models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    // Find all files uploaded by a specific user
    List<FileEntity> findByUploadedBy(user uploadedBy);

    // Find a file by its file name (optional use case)
    List<FileEntity> findByFileName(String fileName);
}