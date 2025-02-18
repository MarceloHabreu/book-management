package io.github.marcelohabreu.bookManagement.exceptions.loan;

public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException() {
        super("This book is already borrowed.");
    }
}
