package ru.migplus.site.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.migplus.site.dao.main.filter.RoleSpec;
import ru.migplus.site.dto.role.RoleDto;
import ru.migplus.site.service.RoleService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class RoleController extends BaseController {

    private final RoleService serv;

    @Autowired
    public RoleController(RoleService service) {
        this.serv = service;
    }

    @GetMapping("/roles")
    ResponseEntity<List<RoleDto>> getAll(RoleSpec filter) {
        return serv.getAll(filter);
    }

    @PostMapping("/role/permissions")
    ResponseEntity changePermissions(@RequestBody RoleDto dto) {
        return serv.changePermissions(dto);
    }


}
