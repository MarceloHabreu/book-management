package io.github.marcelohabreu.bookManagement.controllers.admin;

import io.github.marcelohabreu.bookManagement.dtos.UserDTO;
import io.github.marcelohabreu.bookManagement.dtos.UserUpdateDTO;
import io.github.marcelohabreu.bookManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookmanagement/admin/users")
@CrossOrigin("*")
public class AdminUserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> list(@RequestParam(value = "name", required = false, defaultValue = "") String name) {
        return service.listAllUsers(name);
    }

}
