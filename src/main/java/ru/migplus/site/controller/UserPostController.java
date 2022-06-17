package ru.migplus.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.migplus.site.dto.user.UserPostDto;
import ru.migplus.site.service.UserPostService;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserPostController {

    @Autowired
    private UserPostService serv;

    @GetMapping("/user-posts")
    ResponseEntity<List<UserPostDto>> getAll() {
        return serv.getAll();
    }

}
