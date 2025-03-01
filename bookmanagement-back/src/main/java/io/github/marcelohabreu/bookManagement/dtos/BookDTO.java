package io.github.marcelohabreu.bookManagement.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.marcelohabreu.bookManagement.models.Book;

import java.time.LocalDateTime;

public record BookDTO(Long id, String title, String author, boolean isBorrowed, boolean isActive,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime created_at,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime updated_at,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime deleteAt) {

    public Book toModel() {
        return new Book(id, title, author, isBorrowed, isActive, created_at, updated_at, deleteAt);
    }

    public static BookDTO fromModel(Book b) {
        return new BookDTO(b.getId(), b.getTitle(), b.getAuthor(), b.isBorrowed(), b.isActive(), b.getCreated_at(), b.getUpdated_at(), b.getDeletedAt());
    }
}
