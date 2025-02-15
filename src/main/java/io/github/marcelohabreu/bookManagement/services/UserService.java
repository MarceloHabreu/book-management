package io.github.marcelohabreu.bookManagement.services;

import io.github.marcelohabreu.bookManagement.dtos.UserDTO;
import io.github.marcelohabreu.bookManagement.exceptions.user.EmailAlreadyExistsException;
import io.github.marcelohabreu.bookManagement.exceptions.user.UserNotFoundException;
import io.github.marcelohabreu.bookManagement.models.User;
import io.github.marcelohabreu.bookManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private void checkEmail(User u, Long id) {
        Optional<User> emailAlreadyExists = repository.findByEmail(u.getEmail());
        if (emailAlreadyExists.isPresent() && !emailAlreadyExists.get().getId().equals(id)) {
            throw new EmailAlreadyExistsException();
        }
    }

    @Transactional
    public ResponseEntity<String> saveUser(UserDTO u) {
        User newUser = u.toModel();
        checkEmail(newUser, newUser.getId());

        repository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered.");
    }

    public ResponseEntity<UserDTO> getUserById(Long id) {
        return repository.findById(id)
                .map(UserDTO::fromModel)
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);
    }

    public ResponseEntity<List<UserDTO>> listAllUsers() {
        List<UserDTO> users = repository.findAll().stream().map(UserDTO::fromModel).toList();
        return ResponseEntity.ok(users);
    }

    @Transactional
    public ResponseEntity<String> updateUser(UserDTO u, Long id) {
        Optional<User> userExists = repository.findById(id);

        if (userExists.isEmpty()) {
            throw new UserNotFoundException();
        }

        User userUpdated = u.toModel();
        userUpdated.setId(id);
        userUpdated.setName(u.name());
        userUpdated.setEmail(u.email());

        checkEmail(userUpdated, id);

        repository.save(userUpdated);
        return ResponseEntity.status(HttpStatus.OK).body("User successfully updated.");

    }

    public ResponseEntity<String> deleteUser(Long id) {
        return repository.findById(id)
                .map(user -> {
                    repository.delete(user);
                    return ResponseEntity.status(HttpStatus.OK).body("User successfully deleted.");
                }).orElseThrow(UserNotFoundException::new);
    }
}
