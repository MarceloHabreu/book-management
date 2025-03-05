package io.github.marcelohabreu.bookManagement.controllers.admin;

import io.github.marcelohabreu.bookManagement.models.Dashboard;
import io.github.marcelohabreu.bookManagement.repositories.projection.LoanByMonth;
import io.github.marcelohabreu.bookManagement.services.BookService;
import io.github.marcelohabreu.bookManagement.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/bookmanagement/admin/dashboard")
@CrossOrigin("*")
public class AdminDasboardController {
    private final DashboardService service;
    private final BookService bookService;


    @Autowired
    public AdminDasboardController(DashboardService service, BookService bookService) {
        this.service = service;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Dashboard> getDashboardStats(@RequestParam(value = "year", required = false) Integer year) {
        if (year == null){
            year = LocalDateTime.now().getYear();
        }
        long totalAdmins = service.getAdminCount();
        long totalUsers = service.getUserCount();
        long totalBooks = service.getBookCount();
        long totalBorrowed = service.getBorrowedBookCount();
        List<LoanByMonth> loasnByMonth = service.getLoansByMonth(year);

        return ResponseEntity.ok(new Dashboard(totalAdmins,totalUsers,totalBooks,totalBorrowed,loasnByMonth));
    }

}
