package io.github.marcelohabreu.bookManagement.controllers.user;

import io.github.marcelohabreu.bookManagement.dtos.LoanDTO;
import io.github.marcelohabreu.bookManagement.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmanagement/users/loans")
public class UserLoanController {

    @Autowired
    private LoanService service;

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<String> createLoan(@PathVariable Long userId, @PathVariable Long bookId) {
        return service.createLoan(userId, bookId);
    }

    @PutMapping("{loanId}/return")
    public ResponseEntity<String> returnBook(@PathVariable Long loanId) {
        return service.returnBook(loanId);
    }


}
