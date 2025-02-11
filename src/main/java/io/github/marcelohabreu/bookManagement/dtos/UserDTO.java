package io.github.marcelohabreu.bookManagement.dtos;

import io.github.marcelohabreu.bookManagement.models.User;

public record UserDTO(Long id, String name, String email) {

    public User toModel(){
        return new User(id, name, email);
    }

    public static UserDTO fromModel(User u){
        return new UserDTO(u.getId(), u.getName(), u.getEmail());
    }
}
