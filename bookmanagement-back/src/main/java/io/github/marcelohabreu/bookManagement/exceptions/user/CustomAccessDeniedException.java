package io.github.marcelohabreu.bookManagement.exceptions.user;

import java.nio.file.AccessDeniedException;

public class CustomAccessDeniedException extends AccessDeniedException {
    public CustomAccessDeniedException(String message) {
        super(message);
    }
}
