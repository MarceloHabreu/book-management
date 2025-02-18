package io.github.marcelohabreu.bookManagement.exceptions.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found, try again!");
    }
}
