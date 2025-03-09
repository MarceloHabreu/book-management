package io.github.marcelohabreu.bookManagement.exceptions;

import io.github.marcelohabreu.bookManagement.exceptions.book.BookAlreadyExistException;
import io.github.marcelohabreu.bookManagement.exceptions.book.BooksLoanException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.BookAlreadyReturnedException;
import io.github.marcelohabreu.bookManagement.exceptions.book.BookNotFoundException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.BookAlreadyBorrowedException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.LoanNotFoundException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.UserHasActiveLoanException;
import io.github.marcelohabreu.bookManagement.exceptions.user.EmailAlreadyExistsException;
import io.github.marcelohabreu.bookManagement.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    // User
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAcessDeniedException(AccessDeniedException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistyException(EmailAlreadyExistsException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Book
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BookAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleBookAlreadyExistyException(BookAlreadyExistException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BooksLoanException.class)
    public ResponseEntity<Map<String, String>> handleBookIsLoanException(BooksLoanException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BookAlreadyBorrowedException.class)
    public ResponseEntity<Map<String, String>> handleBookAlreadyBorrowedException(BookAlreadyBorrowedException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Loan
    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleLoanNotFoundException(LoanNotFoundException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ResponseEntity<Map<String, String>> handleBookAlreadyReturnedException(BookAlreadyReturnedException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UserHasActiveLoanException.class)
    public ResponseEntity<Map<String, String>> handleUserHasActiveLoanException(UserHasActiveLoanException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}


