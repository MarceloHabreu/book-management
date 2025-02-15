package io.github.marcelohabreu.bookManagement.repositories;

import io.github.marcelohabreu.bookManagement.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT COUNT(l) FROM Loan l WHERE l.user.id = :userId AND l.returnDate IS NULL")
    int countActiveLoansByUser(@Param("userId") Long userId);
}
