package io.github.marcelohabreu.bookManagement.services;

import io.github.marcelohabreu.bookManagement.dtos.UserDTO;
import io.github.marcelohabreu.bookManagement.dtos.UserUpdateDTO;
import io.github.marcelohabreu.bookManagement.exceptions.user.EmailAlreadyExistsException;
import io.github.marcelohabreu.bookManagement.exceptions.user.UserNotFoundException;
import io.github.marcelohabreu.bookManagement.models.User;
import io.github.marcelohabreu.bookManagement.repositories.LoanRepository;
import io.github.marcelohabreu.bookManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private LoanRepository loanRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void checkEmail(User u) {
        Optional<User> emailAlreadyExists = repository.findByEmail(u.getEmail());
        if (emailAlreadyExists.isPresent()) {
            throw new EmailAlreadyExistsException();
        }
    }

    public ResponseEntity<UserDTO> getUserById(Long id) {
        return repository.findById(id)
                .map(UserDTO::fromModel)
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);
    }

    public ResponseEntity<List<UserDTO>> listAllUsers(String name) {
        List<UserDTO> users = repository.findAllByName("%" + name + "%").stream().map(UserDTO::fromModel).toList();
        return ResponseEntity.ok(users);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateProfile(Long id,UserUpdateDTO u) {

        User userUpdated = repository.findById(id).orElseThrow(UserNotFoundException::new);

        if (u.name() != null) userUpdated.setName(u.name());
        if (u.email() != null) {
            // check email
            Optional<User> existingUser = repository.findByEmail(u.email());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                throw new EmailAlreadyExistsException();
            }
            userUpdated.setEmail(u.email());
        }
        if (u.password() != null) {
            userUpdated.setPassword(u.password());
        }

        repository.save(userUpdated);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Account successfully updated.");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    public ResponseEntity<Map<String, String>> deleteProfile(Long id) {
        if (loanRepository.existsByUserId(id)) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "You must return all borrowed books before deleting your account.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return repository.findById(id)
                .map(user -> {
                    repository.delete(user);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Account successfully deleted.");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }).orElseThrow(UserNotFoundException::new);
    }
}
