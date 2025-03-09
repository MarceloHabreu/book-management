package io.github.marcelohabreu.bookManagement.controllers.user;

import io.github.marcelohabreu.bookManagement.dtos.BookDTO;
import io.github.marcelohabreu.bookManagement.dtos.BookResponse;
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
    public ResponseEntity<List<BookResponse>> list(@RequestParam(value = "title", required = false, defaultValue = "") String title, @RequestParam(value = "author", required = false, defaultValue = "") String author) {
        return service.listActiveBooksToUsers(title, author);
    }

}
