package io.github.marcelohabreu.bookManagement.exceptions.book;

public class BookIsLoanException extends RuntimeException {
    public BookIsLoanException() {
        super("The book is on loan and cannot be deleted!");
    }
}
