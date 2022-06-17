package ru.migplus.site.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.migplus.site.domain.EnvUnit;
import ru.migplus.site.dto.envUnit.*;
import ru.migplus.site.dto.envUnitOper.EnvUnitOperDto;
import ru.migplus.site.service.EnvUnitOperService;
import ru.migplus.site.service.EnvUnitService;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class EnvUnitOperController extends BaseController {

    private final EnvUnitOperService serv;

    @Autowired
    public EnvUnitOperController(EnvUnitOperService service) {
        this.serv = service;
    }

    @DeleteMapping("/envunitoper/{id}")
    public ResponseEntity deleteOper(@PathVariable Long id) {

        EnvUnitOperDto dto = new EnvUnitOperDto();
        dto.setId(id);

        return serv.delete(dto);

    }
}