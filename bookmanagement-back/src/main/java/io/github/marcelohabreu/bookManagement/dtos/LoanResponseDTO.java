package io.github.marcelohabreu.bookManagement.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.marcelohabreu.bookManagement.models.Book;
import io.github.marcelohabreu.bookManagement.models.Loan;

import java.time.LocalDateTime;

public record LoanResponseDTO(Long id, Book book, @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime loanDate,
                              @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime returnDate) {

    public static LoanResponseDTO fromModel(Loan l) {
        return new LoanResponseDTO(l.getId(),l.getBook(), l.getLoanDate(), l.getReturnDate());
    }
}
