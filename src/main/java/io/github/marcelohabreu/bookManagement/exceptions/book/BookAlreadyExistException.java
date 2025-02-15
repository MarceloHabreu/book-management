package io.github.marcelohabreu.bookManagement.exceptions.book;

public class BookAlreadyExistException extends RuntimeException {
    public BookAlreadyExistException() {
        super("This book already exists!");
    }
}
