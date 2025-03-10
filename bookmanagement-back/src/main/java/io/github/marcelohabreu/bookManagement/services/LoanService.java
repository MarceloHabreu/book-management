package io.github.marcelohabreu.bookManagement.services;

import io.github.marcelohabreu.bookManagement.dtos.LoanDTO;
import io.github.marcelohabreu.bookManagement.dtos.LoanResponseDTO;
import io.github.marcelohabreu.bookManagement.exceptions.loan.BookAlreadyReturnedException;
import io.github.marcelohabreu.bookManagement.exceptions.book.BookNotFoundException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.BookAlreadyBorrowedException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.LoanNotFoundException;
import io.github.marcelohabreu.bookManagement.exceptions.loan.UserHasActiveLoanException;
import io.github.marcelohabreu.bookManagement.exceptions.user.UserNotFoundException;
import io.github.marcelohabreu.bookManagement.models.Book;
import io.github.marcelohabreu.bookManagement.models.Loan;
import io.github.marcelohabreu.bookManagement.models.User;
import io.github.marcelohabreu.bookManagement.repositories.BookRepository;
import io.github.marcelohabreu.bookManagement.repositories.LoanRepository;
import io.github.marcelohabreu.bookManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public ResponseEntity<List<LoanDTO>> listAllLoans() {
        List<LoanDTO> loans = loanRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Loan::getId))
                .map(LoanDTO::fromModel)
                .toList();
        return ResponseEntity.ok(loans);
    }

    public ResponseEntity<List<LoanResponseDTO>> listLoansByUser(Long id, String status) {
        List<Loan> loans;
        if ("active".equals(status)){
            loans = loanRepository.findActiveLoansByUser(id);
        } else if ("historic".equals(status)) {
            loans = loanRepository.findHistoricLoansByUser(id);
        } else {
            loans = loanRepository.findAllLoansByUser(id);
        }
        List<LoanResponseDTO> loanDTOS = loans.stream()
                .sorted(Comparator.comparing(Loan::getId))
                .map(LoanResponseDTO::fromModel)
                .toList();
        return ResponseEntity.ok(loanDTOS);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> createLoan(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        if (book.isBorrowed()) {
            throw new BookAlreadyBorrowedException();
        }

        int activeLoans = loanRepository.countActiveLoansByUser(userId);
        if (activeLoans >= 2) {
            throw new UserHasActiveLoanException();
        }

        Loan newLoan = new Loan();
        newLoan.setUser(user);
        newLoan.setBook(book);
        newLoan.setReturnDate(null);

        book.setBorrowed(true);

        loanRepository.save(newLoan);
        bookRepository.save(book);

        // Return message success
        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully borrowed book");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional
    public ResponseEntity<LoanDTO> getLoanById(Long loanId) {
        Loan loanFound = checkLoan(loanId);
        LoanDTO loan = LoanDTO.fromModel(loanFound);
        return ResponseEntity.ok(loan);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> returnBook(Long loanId) {
        Loan loan = checkLoan(loanId);

        if (loan.getReturnDate() != null) {
            throw new BookAlreadyReturnedException();
        }

        loan.setReturnDate(LocalDateTime.now());
        loan.getBook().setBorrowed(false);

        loanRepository.save(loan);
        bookRepository.save(loan.getBook());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Book successfully returned.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private Loan checkLoan(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(LoanNotFoundException::new);
    }

}
