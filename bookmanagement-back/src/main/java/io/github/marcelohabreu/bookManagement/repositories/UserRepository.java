package io.github.marcelohabreu.bookManagement.repositories;

import io.github.marcelohabreu.bookManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE " +
            "upper(u.name) LIKE upper(:name) " +
            "ORDER BY u.id")
    List<User> findAllByName(@Param("name") String name);

    @Query("SELECT COUNT(DISTINCT u) FROM User u JOIN u.roles r WHERE r.name = :roleName")
    long countByRoleName(@Param("roleName") String roleName);
}
