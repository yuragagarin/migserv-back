package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dto.EnvUnitTypeDto;

import java.util.List;

public interface EnvUnitTypeService {
    ResponseEntity<List<EnvUnitTypeDto>> getAll();
}
