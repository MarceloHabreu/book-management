package io.github.marcelohabreu.bookManagement.dtos.auth;


public record LoginResponse(String message, String accessToken, Long expiresIn) {
}
