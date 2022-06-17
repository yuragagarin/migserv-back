package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dao.main.filter.RoleSpec;
import ru.migplus.site.dto.role.RoleDto;

import java.util.List;

public interface RoleService {
    ResponseEntity<List<RoleDto>> getAll(RoleSpec filter);

    ResponseEntity changePermissions(RoleDto dto);
}
