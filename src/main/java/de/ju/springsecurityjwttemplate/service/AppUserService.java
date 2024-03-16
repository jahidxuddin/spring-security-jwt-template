package de.ju.springsecurityjwttemplate.service;

import de.ju.springsecurityjwttemplate.entity.AppUser;
import de.ju.springsecurityjwttemplate.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository repository;

    public List<AppUser> findAllUser() {
        return repository.findAll();
    }
}
