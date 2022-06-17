package ru.migplus.site.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.migplus.site.dto.EnvUnitTypeDto;
import ru.migplus.site.service.EnvUnitTypeService;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class EnvUnitTypeController {

    private final EnvUnitTypeService serv;

    @Autowired
    public EnvUnitTypeController(EnvUnitTypeService service) {
        this.serv = service;
    }

    @GetMapping("/envunittypes")
        //@PreAuthorize("hasAuthority('admin') or hasAuthority('serviceman')")
    ResponseEntity<List<EnvUnitTypeDto>> getAll() {
        return serv.getAll();
    }

}
