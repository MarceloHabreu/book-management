package io.github.marcelohabreu.bookManagement.exceptions.book;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("Book not found, try again!");
    }
}
