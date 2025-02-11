package io.github.marcelohabreu.bookManagement.services;

import io.github.marcelohabreu.bookManagement.dtos.UserDTO;
import io.github.marcelohabreu.bookManagement.exceptions.EmailAlreadyExistsException;
import io.github.marcelohabreu.bookManagement.models.User;
import io.github.marcelohabreu.bookManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public ResponseEntity<?> saveUser(UserDTO u){
        User newUser = u.toModel();
        Optional<User> emailAlreadyExists = repository.findByEmail(newUser.getEmail());
        if (emailAlreadyExists.isPresent()){
            throw new EmailAlreadyExistsException("The e-mail you are trying to register already exists, try again!");
        }

        repository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered");
    }
}
