package io.github.marcelohabreu.bookManagement.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.marcelohabreu.bookManagement.models.Role;
import io.github.marcelohabreu.bookManagement.models.User;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record UserDTO(Long id, String name, String email,
                      Set<String> roles,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime created_at
) {

    public User toModel() {
        return new User(id, name, email, created_at);
    }

    public static UserDTO fromModel(User u) {
        Set<String> roleNames = u.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        return new UserDTO(u.getId(), u.getName(), u.getEmail(),roleNames, u.getCreated_at());
    }
}
