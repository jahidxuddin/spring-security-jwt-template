package de.ju.springsecurityjwttemplate.model;

public record AuthRegisterRequest(String email, String password, String roles) {
}
