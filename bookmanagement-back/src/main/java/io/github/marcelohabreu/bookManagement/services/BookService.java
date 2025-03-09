package io.github.marcelohabreu.bookManagement.services;

import io.github.marcelohabreu.bookManagement.dtos.BookDTO;
import io.github.marcelohabreu.bookManagement.dtos.BookResponse;
import io.github.marcelohabreu.bookManagement.exceptions.book.BookAlreadyExistException;
import io.github.marcelohabreu.bookManagement.exceptions.book.BooksLoanException;
import io.github.marcelohabreu.bookManagement.exceptions.book.BookNotFoundException;
import io.github.marcelohabreu.bookManagement.models.Book;
import io.github.marcelohabreu.bookManagement.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    private void checkBookToSave(Book b) {
        Optional<Book> bookExists = repository.findByTitleAndAuthor(b.getTitle(), b.getAuthor());
        if (bookExists.isPresent() && !bookExists.get().getId().equals(b.getId())) {
            throw new BookAlreadyExistException();
        }
    }

    private void checkBookToDelete(Long id) {
        Optional<Book> bookIsLoan = repository.findByIdAndIsBorrowedTrue(id);
        if (bookIsLoan.isPresent()) {
            throw new BooksLoanException();
        }
    }

    @Transactional
    public ResponseEntity<Map<String, String>> saveBook(BookDTO b) {
            Book newBook = b.toModel();
            checkBookToSave(newBook);
            newBook.setActive(true);
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

    public ResponseEntity<List<BookDTO>> listActiveBooks(String title, String author) {
        List<BookDTO> allActiveBooks = repository.findByIsActiveAndTitleOrAuthor(true,"%" + title + "%", "%" + author + "%").stream().sorted(Comparator.comparing(Book::getId)).map(BookDTO::fromModel).toList();
        return ResponseEntity.ok(allActiveBooks);
    }

    public ResponseEntity<List<BookResponse>> listActiveBooksToUsers(String title, String author) {
        List<BookResponse> allActiveBooks = repository.findByIsActiveAndTitleOrAuthor(true,"%" + title + "%", "%" + author + "%").stream().sorted(Comparator.comparing(Book::getId)).map(BookResponse::fromModel).toList();
        return ResponseEntity.ok(allActiveBooks);
    }

    public ResponseEntity<List<BookDTO>> listInactiveBooks(String title, String author) {
        List<BookDTO> allInactiveBooks = repository.findByIsActiveAndTitleOrAuthor(false,"%" + title + "%", "%" + author + "%").stream().sorted(Comparator.comparing(Book::getId)).map(BookDTO::fromModel).toList();
        return ResponseEntity.ok(allInactiveBooks);
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
        bookUpdated.setActive(bookExists.get().isActive());

        checkBookToSave(bookUpdated);

        repository.save(bookUpdated);
        // Return message success
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book successfully updated.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> softDeleteBook(Long id) {
        checkBookToDelete(id);

        return repository.findByIdAndIsActiveTrue(id)
                .map(book -> {
                    book.setActive(false); // take to trash
                    book.setDeletedAt(LocalDateTime.now()); // set time to delete in trash  with 1 week
                    repository.save(book); // save changes
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Book moved to trash successfully.");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }).orElseThrow(BookNotFoundException::new);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> permanentDeleteBook(Long id) {
        checkBookToDelete(id);
        return repository.findById(id)
                .map(book -> {
                    repository.delete(book);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Book successfully deleted.");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }).orElseThrow(BookNotFoundException::new);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> restoreBook(Long id) {
        return repository.findByIdAndIsActiveFalse(id)
                .map(book -> {
                    book.setActive(true); // restore from trash
                    book.setDeletedAt(null); // set null again
                    repository.save(book); // save changes
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Book successfully restored.");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }).orElseThrow(BookNotFoundException::new);
    }

    // @Scheduled(fixedRate = 60 * 1000) // test auto delete with schedule
     @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // auto delete fixed each 24 hours
    public void autoDeleteInactiveBooks() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        // LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
        List<Book> expiredBooks = repository.findByIsActiveFalseAndDeletedAtBefore(oneWeekAgo);
        repository.deleteAll(expiredBooks);
        // Log
        System.out.println("Deleted " + expiredBooks.size() + " expired books from trash.");
    }

}
