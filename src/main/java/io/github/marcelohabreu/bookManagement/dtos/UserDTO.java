package io.github.marcelohabreu.bookManagement.dtos;

import io.github.marcelohabreu.bookManagement.models.User;

import java.time.LocalDateTime;

public record UserDTO(Long id, String name, String email, LocalDateTime created_at) {

    public User toModel(){
        return new User(id, name, email, created_at);
    }

    public static UserDTO fromModel(User u){
        return new UserDTO(u.getId(), u.getName(), u.getEmail(), u.getCreated_at());
    }
}
