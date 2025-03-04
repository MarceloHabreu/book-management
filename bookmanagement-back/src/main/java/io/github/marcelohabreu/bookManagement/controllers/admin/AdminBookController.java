package io.github.marcelohabreu.bookManagement.controllers.admin;

import io.github.marcelohabreu.bookManagement.dtos.BookDTO;
import io.github.marcelohabreu.bookManagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookmanagement/admin/books")
@CrossOrigin("*")
public class AdminBookController {

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
    public ResponseEntity<List<BookDTO>> getActiveBooks() {
        return service.listActiveBooks();
    }

    @GetMapping("/trash")
    public ResponseEntity<List<BookDTO>> getInactiveBooks() {
        return service.listInactiveBooks();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> update(@RequestBody BookDTO b, @PathVariable Long id) {
        return service.updateBook(b, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> softDelete(@PathVariable Long id) {
        return service.softDeleteBook(id);
    }

    @DeleteMapping("/trash/{id}")
    public ResponseEntity<Map<String, String>> permanentDelete(@PathVariable Long id) {
        return service.permanentDeleteBook(id);
    }

    @PatchMapping("/trash/{id}/restore")
    public ResponseEntity<Map<String, String>> restoreBook(@PathVariable Long id){
        return service.restoreBook(id);
    }
}
