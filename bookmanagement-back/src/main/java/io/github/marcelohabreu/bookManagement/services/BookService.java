package io.github.marcelohabreu.bookManagement.services;

import io.github.marcelohabreu.bookManagement.dtos.BookDTO;
import io.github.marcelohabreu.bookManagement.exceptions.book.BookAlreadyExistException;
import io.github.marcelohabreu.bookManagement.exceptions.book.BookNotFoundException;
import io.github.marcelohabreu.bookManagement.models.Book;
import io.github.marcelohabreu.bookManagement.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    private void checkBook(Book b) {
        Optional<Book> bookExists = repository.findByTitleAndAuthor(b.getTitle(), b.getAuthor());
        if (bookExists.isPresent() && !bookExists.get().getId().equals(b.getId())) {
            throw new BookAlreadyExistException();
        }
    }

    @Transactional
    public ResponseEntity<Map<String, String>> saveBook(BookDTO b) {
            Book newBook = b.toModel();
            checkBook(newBook);
            repository.save(newBook);

            // Return message success
            Map<String, String> response = new HashMap<>();
            response.put("message", "Book successfully registered.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<BookDTO> getBookById(Long id) {
        return repository.findById(id)
                .map(BookDTO::fromModel)
                .map(ResponseEntity::ok)
                .orElseThrow(BookNotFoundException::new);
    }

    public ResponseEntity<List<BookDTO>> listAllBooks() {
        List<BookDTO> allBooks = repository.findAll().stream().sorted(Comparator.comparing(Book::getId)).map(BookDTO::fromModel).toList();
        return ResponseEntity.ok(allBooks);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateBook(BookDTO b, Long id) {
        Optional<Book> bookExists = repository.findById(id);

        if (bookExists.isEmpty()) {
            throw new BookNotFoundException();
        }

        Book bookUpdated = b.toModel();
        bookUpdated.setId(id);
        bookUpdated.setTitle(b.title());
        bookUpdated.setAuthor(b.author());
        bookUpdated.setCreated_at(bookExists.get().getCreated_at());
        bookUpdated.setBorrowed(bookExists.get().isBorrowed());

        checkBook(bookUpdated);

        repository.save(bookUpdated);
        // Return message success
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book successfully updated.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Map<String, String>> deleteBook(Long id) {
        return repository.findById(id)
                .map(book -> {
                    repository.delete(book);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Book successfully deleted.");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }).orElseThrow(BookNotFoundException::new);
    }
}
