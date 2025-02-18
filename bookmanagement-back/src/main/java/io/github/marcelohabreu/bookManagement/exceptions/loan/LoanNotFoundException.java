package io.github.marcelohabreu.bookManagement.exceptions.loan;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException() {
        super("Loan not found, try again!");
    }
}
