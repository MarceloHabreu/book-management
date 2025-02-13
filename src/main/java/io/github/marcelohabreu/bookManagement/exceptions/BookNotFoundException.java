package io.github.marcelohabreu.bookManagement.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
