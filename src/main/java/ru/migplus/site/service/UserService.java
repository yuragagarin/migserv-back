package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dao.main.filter.UserSpec;
import ru.migplus.site.dto.user.UserDto;
import ru.migplus.site.dto.user.UserViewDto;

import java.util.List;

public interface UserService {

    ResponseEntity<List<UserViewDto>> getAll();

    ResponseEntity<List<UserDto>> getAll(UserSpec filter);

    ResponseEntity create(UserDto dto);

    ResponseEntity update(UserDto dto);

    ResponseEntity delete(UserDto dto);

    ResponseEntity<UserDto> get(long id);

}
