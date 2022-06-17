package ru.migplus.site.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.migplus.site.dao.main.filter.PermissionSpec;
import ru.migplus.site.dto.role.PermissionDto;
import ru.migplus.site.dto.role.Views;
import ru.migplus.site.service.PermissionService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class PermissionController extends BaseController {

    private final PermissionService serv;

    @Autowired
    public PermissionController(PermissionService service) {
        this.serv = service;
    }

    @GetMapping("/permissions/idn")
    @JsonView(Views.IdName.class)
    ResponseEntity<List<PermissionDto>> getIdName(PermissionSpec filter) {
        return serv.getAll(filter);
    }

    @GetMapping("/permissions/dict")
    @JsonView(Views.Dict.class)
    ResponseEntity<List<PermissionDto>> getDict(PermissionSpec filter) {
        return serv.getAll(filter);
    }

    @PostMapping(value = "/permission")
    public ResponseEntity create(@RequestBody PermissionDto dto) {

        dto.setUserId(getAuthUserId());

        return serv.create(dto);

    }

    @DeleteMapping("/permission/{id}")
    public void delete(@PathVariable int id) {

        PermissionDto dto = new PermissionDto();
        dto.setId(id);
        dto.setUserId(getAuthUserId());
        serv.delete(dto);

    }

}
