package io.github.marcelohabreu.bookManagement.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.marcelohabreu.bookManagement.models.Book;
import io.github.marcelohabreu.bookManagement.models.Loan;
import io.github.marcelohabreu.bookManagement.models.User;

import java.time.LocalDateTime;

public record LoanDTO(Long id, User user, Book book,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime loanDate,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime returnDate) {

    public Loan toModel() {
        return new Loan(id, user, book, loanDate, returnDate);
    }

    public static LoanDTO fromModel(Loan l) {
        return new LoanDTO(l.getId(), l.getUser(), l.getBook(), l.getLoanDate(), l.getReturnDate());
    }
}
