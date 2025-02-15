package io.github.marcelohabreu.bookManagement.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.marcelohabreu.bookManagement.models.Book;

import java.time.LocalDateTime;

public record BookDTO(Long id, String title, String author, boolean isBorrowed,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime created_at,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime updated_at) {

    public Book toModel() {
        return new Book(id, title, author, isBorrowed, created_at, updated_at);
    }

    public static BookDTO fromModel(Book b) {
        return new BookDTO(b.getId(), b.getTitle(), b.getAuthor(),b.isBorrowed(), b.getCreated_at(), b.getUpdated_at());
    }
}
