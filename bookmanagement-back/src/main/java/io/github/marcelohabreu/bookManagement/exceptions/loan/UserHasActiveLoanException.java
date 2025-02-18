package io.github.marcelohabreu.bookManagement.exceptions.loan;

public class UserHasActiveLoanException extends RuntimeException {
    public UserHasActiveLoanException() {
        super("User has already borrowed two books");
    }
}
