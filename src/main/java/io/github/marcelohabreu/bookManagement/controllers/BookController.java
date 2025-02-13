package io.github.marcelohabreu.bookManagement.controllers;

import io.github.marcelohabreu.bookManagement.dtos.BookDTO;
import io.github.marcelohabreu.bookManagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmanagement/books")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody BookDTO b){
        return service.saveBook(b);
    }
}
