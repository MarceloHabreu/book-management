package io.github.marcelohabreu.bookManagement.controllers;

import io.github.marcelohabreu.bookManagement.dtos.auth.LoginRequest;
import io.github.marcelohabreu.bookManagement.dtos.auth.LoginResponse;
import io.github.marcelohabreu.bookManagement.dtos.auth.RegisterRequest;
import io.github.marcelohabreu.bookManagement.exceptions.user.CustomBadCredentialsException;
import io.github.marcelohabreu.bookManagement.models.Role;
import io.github.marcelohabreu.bookManagement.models.User;
import io.github.marcelohabreu.bookManagement.repositories.RoleRepository;
import io.github.marcelohabreu.bookManagement.repositories.UserRepository;
import io.github.marcelohabreu.bookManagement.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth")
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, UserService userService){
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login){
        if (login.email() == null || login.password() == null) {
            throw new IllegalArgumentException("Email, and password are required");
        }
        var user = userRepository.findByEmail(login.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(login, passwordEncoder)) {
           throw new CustomBadCredentialsException();
        }
        var now = Instant.now();
        var expiresIn = 300L; // 5 min

        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();


        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponse("Successful login",jwtValue, expiresIn));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest register){
        if (register.name() == null || register.email() == null || register.password() == null) {
            throw new IllegalArgumentException("Name, email, and password are required");
        }
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name()).orElseThrow(() -> new IllegalStateException("Role 'basic' not found"));

        User newUser = new User();
        newUser.setName(register.name());
        newUser.setEmail(register.email());
        newUser.setPassword(passwordEncoder.encode(register.password()));
        newUser.setRoles(Set.of(basicRole));
        userService.checkEmail(newUser);

        userRepository.save(newUser);

        // Create the token JWT
        var now = Instant.now();
        var expiresIn = 300L; // 5 min

        var scopes = newUser.getRoles()
                .stream()
                .map(role -> "SCOPE_" + role.getName())
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(newUser.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue =  jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponse("Successful registration",jwtValue, expiresIn));
    }
}
