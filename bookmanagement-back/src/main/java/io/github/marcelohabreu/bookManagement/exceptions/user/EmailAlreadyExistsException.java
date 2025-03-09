package io.github.marcelohabreu.bookManagement.exceptions.user;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("The e-mail you are trying to register already exists. Please try again.");
    }
}
