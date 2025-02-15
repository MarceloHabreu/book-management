package io.github.marcelohabreu.bookManagement.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    @Column(name = "is_borrowed")
    private boolean isBorrowed = false;

    @CreationTimestamp
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updated_at;

    public Book(Long id, String title, String author, LocalDateTime createdAt, LocalDateTime updatedAt) {
    }
}
