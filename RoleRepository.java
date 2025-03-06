package com.example.file_share.repositories;

import com.example.file_share.models.role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<role, Long> {

    // Find a Role by its name
    Optional<role> findByName(String name);
}