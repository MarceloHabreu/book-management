package io.github.marcelohabreu.bookManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    // User
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExistyException(EmailAlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Book
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExistyException(BookAlreadyExistException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}


