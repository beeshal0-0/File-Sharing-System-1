package com.example.file_share.service;

import com.example.file_share.models.role;
import com.example.file_share.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Create a new Role
    public role createRole(role role) {
        // Check if role already exists by name
        if (roleRepository.findByName(role.getName()).isPresent()) {
            throw new IllegalArgumentException("Role with the given name already exists.");
        }
        // Save and return the role
        return roleRepository.save(role);
    }

    // Get role details by name
    public role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with name: " + name));
    }
}