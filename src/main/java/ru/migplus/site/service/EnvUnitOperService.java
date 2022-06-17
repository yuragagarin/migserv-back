package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dto.envUnitOper.EnvUnitOperDto;

public interface EnvUnitOperService {

    ResponseEntity delete(EnvUnitOperDto dto);

}
