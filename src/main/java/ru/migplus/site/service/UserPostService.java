package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dto.user.UserPostDto;

import java.util.List;

public interface UserPostService {
    ResponseEntity<List<UserPostDto>> getAll();
}
