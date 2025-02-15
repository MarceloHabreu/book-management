package io.github.marcelohabreu.bookManagement.controllers;

import io.github.marcelohabreu.bookManagement.dtos.LoanDTO;
import io.github.marcelohabreu.bookManagement.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmanagement/loans")
public class LoanController {

    @Autowired
    private LoanService service;

    @GetMapping
    public ResponseEntity<List<LoanDTO>> list() {
        return service.listAllLoans();
    }

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<String> createLoan(@PathVariable Long userId, @PathVariable Long bookId) {
        return service.createLoan(userId, bookId);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanDTO> getLoan(@PathVariable Long loanId) {
        return service.getLoanById(loanId);
    }

    @PutMapping("/{loanId}/return")
    public ResponseEntity<String> returnBook(@PathVariable Long loanId) {
        return service.returnBook(loanId);
    }


}
