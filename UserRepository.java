/*package com.example.file_share.repositories;

import com.example.file_share.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

package com.example.file_share.service;

import com.example.file_share.models.User;
import com.example.file_share.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // Custom query method to find a user by username
}

 */
package com.example.file_share.repositories;

import com.example.file_share.models.user; // Import the lowercase `user`
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository interface for user entity
public interface UserRepository extends JpaRepository<user, Long> {
    Optional<user> findByUsername(String username);
    Optional<user> findByEmail(String email);// Find a user by their username
}

