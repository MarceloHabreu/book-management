package io.github.marcelohabreu.bookManagement.controllers.admin;

import io.github.marcelohabreu.bookManagement.dtos.LoanDTO;
import io.github.marcelohabreu.bookManagement.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmanagement/admin/loans")
public class AdminLoanController {

    @Autowired
    private LoanService service;

    @GetMapping
    public ResponseEntity<List<LoanDTO>> list() {
        return service.listAllLoans();
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanDTO> getLoan(@PathVariable Long loanId) {
        return service.getLoanById(loanId);
    }



}
