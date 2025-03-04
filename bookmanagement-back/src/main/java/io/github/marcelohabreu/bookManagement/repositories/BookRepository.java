package io.github.marcelohabreu.bookManagement.repositories;

import io.github.marcelohabreu.bookManagement.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleAndAuthor(String title, String author);

    List<Book> findByIsActiveFalseAndDeletedAtBefore(LocalDateTime date);

    List<Book> findByIsActiveTrue();

    List<Book> findByIsActiveFalse();

    Optional<Book> findByIdAndIsBorrowedTrue(Long id);

    Optional<Book> findByIdAndIsActiveFalse(Long id);

    Optional<Book> findByIdAndIsActiveTrue(Long id);

    long countByIsBorrowedTrueAndIsActiveTrue();

}
