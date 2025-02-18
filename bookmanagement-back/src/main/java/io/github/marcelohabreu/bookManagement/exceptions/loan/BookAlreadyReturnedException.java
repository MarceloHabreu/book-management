package io.github.marcelohabreu.bookManagement.exceptions.loan;

public class BookAlreadyReturnedException extends RuntimeException {
    public BookAlreadyReturnedException() {
        super("This book has already been returned!");
    }
}
