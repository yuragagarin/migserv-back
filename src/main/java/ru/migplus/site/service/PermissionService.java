package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dao.main.filter.PermissionSpec;
import ru.migplus.site.dto.role.PermissionDto;

import java.util.List;

public interface PermissionService {
    ResponseEntity<List<PermissionDto>> getAll(PermissionSpec filter);

    ResponseEntity create(PermissionDto dto);

    ResponseEntity delete(PermissionDto dto);
}
