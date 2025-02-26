package io.github.marcelohabreu.bookManagement.controllers;

import io.github.marcelohabreu.bookManagement.dtos.BookDTO;
import io.github.marcelohabreu.bookManagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookmanagement/books")
@CrossOrigin("*")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody BookDTO b) {
        return service.saveBook(b);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        return service.getBookById(id);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBook() {
        return service.listAllBooks();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody BookDTO b, @PathVariable Long id) {
        return service.updateBook(b, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return service.deleteBook(id);
    }
}
