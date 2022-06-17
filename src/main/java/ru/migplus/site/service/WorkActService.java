package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dto.workAct.*;

import java.util.List;

public interface WorkActService {
    ResponseEntity<List<WorkActPosTypeDto>> getPosTypes();

    ResponseEntity create(WorkActDto dto);

    ResponseEntity<List<WorkActViewDto>> getAll(WorkActDto filt);

    ResponseEntity<List<WorkActPosViewDto>> getPoss(WorkActPosDto filt);

    ResponseEntity confirm(WorkActDto dto);

    ResponseEntity cancel(WorkActDto dto);
}
