package io.github.marcelohabreu.bookManagement.repositories;

import io.github.marcelohabreu.bookManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    long countByRole(String roleUser);

    @Query("SELECT u FROM User u WHERE " +
            "upper(u.name) LIKE upper(:name) " +
            "ORDER BY u.id")
    List<User> findByName(@Param("name") String name);
}
