package io.github.marcelohabreu.bookManagement.services;

import io.github.marcelohabreu.bookManagement.repositories.BookRepository;
import io.github.marcelohabreu.bookManagement.repositories.LoanRepository;
import io.github.marcelohabreu.bookManagement.repositories.UserRepository;
import io.github.marcelohabreu.bookManagement.repositories.projection.LoanByMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public DashboardService(BookRepository bookRepository, UserRepository userRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public long getAdminCount() {
        return userRepository.countByRole("ROLE_ADMIN");
    }

    public long getUserCount() {
        return userRepository.countByRole("ROLE_USER");
    }

    public long getBookCount() {
        return bookRepository.count();
    }

    public long getBorrowedBookCount() {
        return bookRepository.countByIsBorrowedTrueAndIsActiveTrue();
    }

    public List<LoanByMonth> getLoansByMonth(Integer year) {
        return loanRepository.getLoansByMonth(year);
    }

}
