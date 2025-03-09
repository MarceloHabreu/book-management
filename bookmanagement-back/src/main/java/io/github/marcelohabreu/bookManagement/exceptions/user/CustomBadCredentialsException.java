package io.github.marcelohabreu.bookManagement.exceptions.user;

import org.springframework.security.authentication.BadCredentialsException;

public class CustomBadCredentialsException extends BadCredentialsException {
    public CustomBadCredentialsException() {
        super("Invalid email or password. Please try again.");
    }
}
