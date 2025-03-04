package io.github.marcelohabreu.bookManagement.exceptions;

import io.github.marcelohabreu.bookManagement.exceptions.book.BookAlreadyExistException;
import io.github.marcelohabreu.bookManagement.exceptions.book.BookIsLoanException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.BookAlreadyReturnedException;
import io.github.marcelohabreu.bookManagement.exceptions.book.BookNotFoundException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.BookAlreadyBorrowedException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.LoanNotFoundException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.UserHasActiveLoanException;
import io.github.marcelohabreu.bookManagement.exceptions.user.EmailAlreadyExistsException;
import io.github.marcelohabreu.bookManagement.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    // User
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

    @ExceptionHandler(BookIsLoanException.class)
    public ResponseEntity<Map<String, String>> handleBookIsLoanException(BookIsLoanException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BookAlreadyBorrowedException.class)
    public ResponseEntity<?> handleBookAlreadyBorrowedException(BookAlreadyBorrowedException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Loan
    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<?> handleLoanNotFoundException(LoanNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ResponseEntity<?> handleBookAlreadyReturnedException(BookAlreadyReturnedException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserHasActiveLoanException.class)
    public ResponseEntity<?> handleUserHasActiveLoanException(UserHasActiveLoanException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}


