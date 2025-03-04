package io.github.marcelohabreu.bookManagement.controllers.user;

import io.github.marcelohabreu.bookManagement.dtos.UserDTO;
import io.github.marcelohabreu.bookManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmanagement/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService service;


    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody UserDTO u, @PathVariable Long id) {
        return service.updateUser(u, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return service.deleteUser(id);
    }
}
