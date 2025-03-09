package io.github.marcelohabreu.bookManagement.controllers.user;

import io.github.marcelohabreu.bookManagement.dtos.LoanDTO;
import io.github.marcelohabreu.bookManagement.dtos.LoanResponseDTO;
import io.github.marcelohabreu.bookManagement.exceptions.user.CustomAccessDeniedException;
import io.github.marcelohabreu.bookManagement.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookmanagement/users/loans")
public class UserLoanController {

    @Autowired
    private LoanService service;

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<Map<String, String>> createLoan(
            @PathVariable Long userId,
            @PathVariable Long bookId) {
        return service.createLoan(userId, bookId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<LoanResponseDTO>> listLoans(@PathVariable Long id, JwtAuthenticationToken token, @RequestParam(required = false) String status) throws CustomAccessDeniedException {
        if (token == null) {
            throw new CustomAccessDeniedException("Authentication required");
        }
        Long authenticatedUserId = Long.valueOf(token.getName());
        if (!authenticatedUserId.equals(id)) {
            throw new CustomAccessDeniedException("You can only see your own loans");
        }

        return service.listLoansByUser(id, status);
    }

    @PatchMapping("/{loanId}/return")
    public ResponseEntity<Map<String, String>> returnBook(@PathVariable Long loanId) {
        return service.returnBook(loanId);
    }
}
