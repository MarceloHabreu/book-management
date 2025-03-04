package io.github.marcelohabreu.bookManagement.controllers.user;

import io.github.marcelohabreu.bookManagement.dtos.BookDTO;
import io.github.marcelohabreu.bookManagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookmanagement/users/books")
@CrossOrigin("*")
public class UserBookController {

    @Autowired
    private BookService service;

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        return service.getBookById(id);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> list() {
        return service.listActiveBooks();
    }

}
