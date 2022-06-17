package ru.migplus.site.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.migplus.site.dao.main.filter.UserSpec;
import ru.migplus.site.dto.consumer.Views;
import ru.migplus.site.dto.user.UserDto;
import ru.migplus.site.dto.user.UserViewDto;
import ru.migplus.site.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class UserController extends BaseController {

    private final UserService serv;

    @Autowired
    public UserController(UserService service) {
        this.serv = service;
    }

    @GetMapping("/users/idn")
    @JsonView(Views.IdName.class)
    ResponseEntity<List<UserDto>> getIdName(UserSpec filter) {
        return serv.getAll(filter);
    }

    @GetMapping("/users/dict")
    ResponseEntity<List<UserViewDto>> getAll() {
        return serv.getAll();
    }

    @PostMapping(value = "/user")
    public ResponseEntity create(@RequestBody UserDto dto) {
        dto.setUserId(getAuthUserId());
        return serv.create(dto);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDto> get(@PathVariable long id) {
        return serv.get(id);
    }

    @PutMapping(value = "/user")
    public ResponseEntity update(@RequestBody UserDto dto) {

        dto.setUserId(getAuthUserId());

        return serv.update(dto);

    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable long id) {

        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setUserId(getAuthUserId());
        serv.delete(dto);

    }
}
