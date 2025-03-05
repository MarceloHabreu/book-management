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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Map<String, String>> saveUser(UserDTO u) {
        User newUser = u.toModel();
        checkEmail(newUser, newUser.getId());
        newUser.setRole("ROLE_USER");

        repository.save(newUser);
        // Return message success
        Map<String, String> response = new HashMap<>();
        response.put("message", "User successfully registered.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<UserDTO> getUserById(Long id) {
        return repository.findById(id)
                .map(UserDTO::fromModel)
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);
    }

    public ResponseEntity<List<UserDTO>> listAllUsers(String name) {
        List<UserDTO> users = repository.findByName("%" + name + "%").stream().map(UserDTO::fromModel).toList();
        return ResponseEntity.ok(users);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateUser(UserDTO u, Long id) {
        Optional<User> userExists = repository.findById(id);

        if (userExists.isEmpty()) {
            throw new UserNotFoundException();
        }

        User userUpdated = u.toModel();
        userUpdated.setId(id);
        userUpdated.setName(u.name());
        userUpdated.setEmail(u.email());
        userUpdated.setRole("ROLE_USER");

        checkEmail(userUpdated, id);

        repository.save(userUpdated);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User successfully updated.");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    public ResponseEntity<Map<String, String>> deleteUser(Long id) {
        return repository.findById(id)
                .map(user -> {
                    repository.delete(user);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "User successfully deleted.");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }).orElseThrow(UserNotFoundException::new);
    }
}
