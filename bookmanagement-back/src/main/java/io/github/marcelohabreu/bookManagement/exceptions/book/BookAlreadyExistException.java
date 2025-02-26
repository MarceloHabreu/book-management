package io.github.marcelohabreu.bookManagement.exceptions.book;

public class BookAlreadyExistException extends RuntimeException {
    public BookAlreadyExistException() {
        super("A book with the same title and author already exists.");
    }
}
