package com.example.file_share.controller;
import com.example.file_share.models.role;
import com.example.file_share.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class Rolecontroller {

    @Autowired
    private RoleService roleService;

    // Create a new role
    @PostMapping("/create")
    public role createRole(@RequestBody role role) {
        return roleService.createRole(role);
    }

    // Get role details by name
    @GetMapping("/{name}")
    public role getRoleByName(@PathVariable String name) {
        return roleService.getRoleByName(name);
    }

    // Other role-related APIs can be added here.
}
