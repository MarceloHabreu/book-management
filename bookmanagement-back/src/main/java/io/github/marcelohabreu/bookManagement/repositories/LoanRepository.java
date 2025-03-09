package io.github.marcelohabreu.bookManagement.repositories;

import io.github.marcelohabreu.bookManagement.models.Loan;
import io.github.marcelohabreu.bookManagement.repositories.projection.LoanByMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT COUNT(l) FROM Loan l WHERE l.user.id = :userId AND l.returnDate IS NULL")
    int countActiveLoansByUser(@Param("userId") Long userId);

    @Query(nativeQuery = true, value =
            "SELECT EXTRACT(MONTH FROM l.loan_date) AS month, COUNT(*) AS quantity " +
                    "FROM loan L " +
                    "WHERE EXTRACT(YEAR FROM l.loan_date) = :year " +
                    "GROUP BY EXTRACT(MONTH FROM l.loan_date) " +
                    "ORDER BY EXTRACT(MONTH FROM l.loan_date)"
    )
    List<LoanByMonth> getLoansByMonth(@Param("year") Integer year);

    @Query("SELECT l FROM Loan l WHERE l.user.id = :userId")
    List<Loan> findAllLoansByUser(@Param("userId") Long id);

    @Query("SELECT l FROM Loan l WHERE l.user.id = :userId AND l.returnDate IS NULL")
    List<Loan> findActiveLoansByUser(@Param("userId") Long userId);

    @Query("SELECT l FROM Loan l WHERE l.user.id = :userId AND l.returnDate IS NOT NULL")
    List<Loan> findHistoricLoansByUser(@Param("userId") Long userId);

    boolean existsByUserId(Long id);

}
