package io.github.marcelohabreu.bookManagement.controllers;

import io.github.marcelohabreu.bookManagement.dtos.UserUpdateDTO;
import io.github.marcelohabreu.bookManagement.exceptions.user.CustomAccessDeniedException;
import io.github.marcelohabreu.bookManagement.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestController
@RequestMapping("/api/bookmanagement/me")
@CrossOrigin("*")
public class ProfileController {
    private final UserService service;
    private final BCryptPasswordEncoder passwordEncoder;

    public ProfileController(UserService service,  BCryptPasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateMe(
            @PathVariable Long id,
            @RequestBody UserUpdateDTO user,
            JwtAuthenticationToken token
    ) throws AccessDeniedException {
        if (token == null) {
            throw new CustomAccessDeniedException("Authentication required");
        }
        Long authenticatedUserId = Long.valueOf(token.getName());
        if (!authenticatedUserId.equals(id)) {
            throw new CustomAccessDeniedException("You can only update your own account");
        }

        UserUpdateDTO encryptedUser = new UserUpdateDTO(
                user.name(),
                user.email(),
                user.password() != null ? passwordEncoder.encode(user.password()) : null
        );
        return service.updateProfile(id, encryptedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteMe(
            @PathVariable Long id, JwtAuthenticationToken token) throws CustomAccessDeniedException {
        if (token == null) {
            throw new CustomAccessDeniedException("Authentication required");
        }
        Long authenticatedUserId = Long.valueOf(token.getName());
        if (!authenticatedUserId.equals(id)) {
            throw new CustomAccessDeniedException("You can only delete your own account");
        }
        return service.deleteProfile(id);
    }
}
