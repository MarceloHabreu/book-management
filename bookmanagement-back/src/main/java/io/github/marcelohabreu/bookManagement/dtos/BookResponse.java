package io.github.marcelohabreu.bookManagement.dtos;

import io.github.marcelohabreu.bookManagement.models.Book;

public record BookResponse(Long id, String title, String author, boolean isBorrowed) {
    public static BookResponse fromModel(Book b) {
        return new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.isBorrowed());
    }
}
