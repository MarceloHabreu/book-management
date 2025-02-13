package io.github.marcelohabreu.bookManagement.repositories;

import io.github.marcelohabreu.bookManagement.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleAndAuthor(String title, String author);
}
