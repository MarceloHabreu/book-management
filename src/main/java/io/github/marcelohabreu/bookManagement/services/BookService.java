package io.github.marcelohabreu.bookManagement.services;

import io.github.marcelohabreu.bookManagement.dtos.BookDTO;
import io.github.marcelohabreu.bookManagement.exceptions.BookAlreadyExistException;
import io.github.marcelohabreu.bookManagement.models.Book;
import io.github.marcelohabreu.bookManagement.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    private void checkBook(Book b){
        Optional<Book> bookExists = repository.findByTitleAndAuthor(b.getTitle(), b.getAuthor());
        if (bookExists.isPresent()){
            throw new BookAlreadyExistException("This book already exists!");
        }
    }

    @Transactional
    public ResponseEntity<String> saveBook(BookDTO b){
        Book newBook = b.toModel();
        checkBook(newBook);

        repository.save(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book successfully registered.");
    }
}
