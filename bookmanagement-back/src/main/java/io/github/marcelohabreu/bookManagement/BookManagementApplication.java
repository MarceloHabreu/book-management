package io.github.marcelohabreu.bookManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BookManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookManagementApplication.class, args);
    }

}
