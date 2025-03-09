package io.github.marcelohabreu.bookManagement.exceptions.book;

public class BooksLoanException extends RuntimeException {
    public BooksLoanException() {
        super("The book is on loan and cannot be deleted!");
    }
}
