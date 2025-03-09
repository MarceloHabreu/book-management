package io.github.marcelohabreu.bookManagement.config;

import io.github.marcelohabreu.bookManagement.models.Role;
import io.github.marcelohabreu.bookManagement.models.User;
import io.github.marcelohabreu.bookManagement.repositories.RoleRepository;
import io.github.marcelohabreu.bookManagement.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name()).orElseThrow(() -> new IllegalStateException("Role 'admin' not found"));
        var userAdmin = userRepository.findByEmail("admin@gmail.com");

        // creating the admin
        userAdmin.ifPresentOrElse(
                (user) -> {
                    System.out.println("admin already exist");
                },
                () -> {
                    var user = new User();
                    user.setName("marceloH");
                    user.setEmail("admin@gmail.com");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                }
        );
    }
}
