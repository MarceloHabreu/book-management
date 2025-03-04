package io.github.marcelohabreu.bookManagement.controllers.admin;

import io.github.marcelohabreu.bookManagement.dtos.UserDTO;
import io.github.marcelohabreu.bookManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmanagement/admin/users")
@CrossOrigin("*")
public class AdminUserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO u) {
        return service.saveUser(u);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> list() {
        return service.listAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody UserDTO u, @PathVariable Long id) {
        return service.updateUser(u, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return service.deleteUser(id);
    }
}
