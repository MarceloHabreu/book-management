package io.github.marcelohabreu.bookManagement.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.marcelohabreu.bookManagement.models.User;

import java.time.LocalDateTime;

public record UserDTO(Long id, String name, String email,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime created_at,
                      String role
) {

    public User toModel() {
        return new User(id, name, email, created_at, role);
    }

    public static UserDTO fromModel(User u) {
        return new UserDTO(u.getId(), u.getName(), u.getEmail(), u.getCreated_at(), u.getRole());
    }
}
