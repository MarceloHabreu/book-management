package io.github.marcelohabreu.bookManagement.models;

import io.github.marcelohabreu.bookManagement.repositories.projection.LoanByMonth;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Dashboard {
    private Long totalAdmins;
    private Long totalUsers;
    private Long totalBooks;
    private Long totalBorrowed;
    private List<LoanByMonth> loansByMonth;

    public Dashboard(Long totalAdmins, Long totalUsers, Long totalBooks, Long totalBorrowed, List<LoanByMonth> loansByMonth) {
        this.totalAdmins = totalAdmins;
        this.totalUsers = totalUsers;
        this.totalBooks = totalBooks;
        this.totalBorrowed = totalBorrowed;
        this.loansByMonth = loansByMonth;
        this.populateMissingMonths();
    }
    public List<LoanByMonth> getLoansByMonth() {
        if (loansByMonth == null) {
            loansByMonth = new ArrayList<>();
        }
        return loansByMonth;
    }

    private void populateMissingMonths() {
        int maximumMonth = getLoansByMonth().stream()
                .mapToInt(LoanByMonth::getMonth)
                .max()
                .orElse(12); // Default para 12 meses

        List<Integer> listMonths = new ArrayList<>();
        for (int i = 1; i <= maximumMonth; i++) {
            Integer integer = i;
            listMonths.add(integer);
        }

        List<Integer> monthsAdded = new ArrayList<>();
        for (LoanByMonth loanByMonth : getLoansByMonth()) {
            Integer loanByMonthMonth = loanByMonth.getMonth();
            monthsAdded.add(loanByMonthMonth);
        }

        listMonths.forEach(month -> {
            if (!monthsAdded.contains(month)) {
                getLoansByMonth().add(new LoanByMonth() {
                    @Override
                    public Integer getMonth() {
                        return month;
                    }

                    @Override
                    public Long getQuantity() {
                        return 0L;
                    }

                });
            }
        });

        getLoansByMonth().sort(Comparator.comparing(LoanByMonth::getMonth));
    }
}