package io.github.marcelohabreu.bookManagement.repositories;

import io.github.marcelohabreu.bookManagement.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleAndAuthor(String title, String author);

    List<Book> findByIsActiveFalseAndDeletedAtBefore(LocalDateTime date);

    Optional<Book> findByIdAndIsBorrowedTrue(Long id);

    Optional<Book> findByIdAndIsActiveFalse(Long id);

    Optional<Book> findByIdAndIsActiveTrue(Long id);

    long countByIsBorrowedTrueAndIsActiveTrue();

    @Query("SELECT b FROM Book b WHERE b.isActive = :isActive " +
            "AND upper(b.title) LIKE upper(:title) " +
            "AND upper(b.author) LIKE upper(:author) " +
            "ORDER BY b.id")
    List<Book> findByIsActiveAndTitleOrAuthor(@Param("isActive") boolean isActive,
                                              @Param("title") String title,
                                              @Param("author") String author);

}
