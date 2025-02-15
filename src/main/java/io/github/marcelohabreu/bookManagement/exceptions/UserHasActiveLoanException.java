package io.github.marcelohabreu.bookManagement.exceptions;

public class UserHasActiveLoanException extends RuntimeException {
    public UserHasActiveLoanException(String message) {
        super(message);
    }
}
