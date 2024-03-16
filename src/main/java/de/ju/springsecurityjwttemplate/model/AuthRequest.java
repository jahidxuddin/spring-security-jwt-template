package de.ju.springsecurityjwttemplate.model;

public record AuthRequest(String email, String password, String roles) {
}
