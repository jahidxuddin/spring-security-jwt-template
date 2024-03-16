package de.ju.springsecurityjwttemplate.controller;

import de.ju.springsecurityjwttemplate.entity.AppUser;
import de.ju.springsecurityjwttemplate.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService userService;

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUser() {
        return ResponseEntity.ok(userService.findAllUser());
    }
}
