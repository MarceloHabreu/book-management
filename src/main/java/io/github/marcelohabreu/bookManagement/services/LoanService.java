package io.github.marcelohabreu.bookManagement.services;

import io.github.marcelohabreu.bookManagement.dtos.LoanDTO;
import io.github.marcelohabreu.bookManagement.exceptions.*;
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
import java.util.List;

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

    @Transactional
    public ResponseEntity<String> createLoan(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found, try again!"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found, try again!"));

        if (book.isBorrowed()) {
            throw new BookAlreadyBorrowedException("This book is already borrowed.");
        }

        int activeLoans = loanRepository.countActiveLoansByUser(userId);
        if (activeLoans >= 2) {
            throw new UserHasActiveLoanException("User has already borrowed two books");
        }

        Loan newLoan = new Loan();
        newLoan.setUser(user);
        newLoan.setBook(book);
        newLoan.setReturnDate(null);

        book.setBorrowed(true);

        loanRepository.save(newLoan);
        bookRepository.save(book);

        return ResponseEntity.status(HttpStatus.CREATED).body("Loan successfully created");
    }

    @Transactional
    public ResponseEntity<LoanDTO> getLoanById(Long loanId){
        Loan loanFound = checkLoan(loanId);
        LoanDTO loan = LoanDTO.fromModel(loanFound);
        return ResponseEntity.ok(loan);
    }

    @Transactional
    public ResponseEntity<String> returnBook(Long loanId) {
        Loan loan = checkLoan(loanId);

        if (loan.getReturnDate() != null) {
            throw new BookAlreadyReturnedException("This book has already been returned!");
        }

        loan.setReturnDate(LocalDateTime.now());
        loan.getBook().setBorrowed(false);

        loanRepository.save(loan);
        bookRepository.save(loan.getBook());

        return ResponseEntity.ok("Book successfully returned.");
    }

    private Loan checkLoan(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found, try again!"));
    }
}
