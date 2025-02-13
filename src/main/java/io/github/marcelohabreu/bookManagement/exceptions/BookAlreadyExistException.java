package io.github.marcelohabreu.bookManagement.exceptions;

public class BookAlreadyExistException extends RuntimeException {
    public BookAlreadyExistException(String message) {
        super(message);
    }
}
