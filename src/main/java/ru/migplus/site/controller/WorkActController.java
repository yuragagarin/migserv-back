package ru.migplus.site.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.migplus.site.dto.workAct.*;
import ru.migplus.site.service.WorkActService;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class WorkActController extends BaseController {

    private final WorkActService serv;

    @Autowired
    public WorkActController(WorkActService service) {
        this.serv = service;
    }

    @GetMapping("/workact/pos/types")
    ResponseEntity<List<WorkActPosTypeDto>> getPosTypes() {
        return serv.getPosTypes();
    }

    @PostMapping("/work-act")
    ResponseEntity create(@RequestBody WorkActDto dto) {
        dto.setUserId(getAuthUserId());
        return serv.create(dto);
    }

    @PostMapping("/work-act/confirm")
    ResponseEntity<List<WorkActViewDto>> confirm(@RequestBody WorkActDto dto) {
        dto.setUserId(getAuthUserId());
        return serv.confirm(dto);
    }

    @PostMapping("/work-act/cancel")
    ResponseEntity<List<WorkActViewDto>> cancelConfrim(@RequestBody WorkActDto dto) {
        dto.setUserId(getAuthUserId());
        return serv.cancel(dto);
    }

    @JsonView(Views.Status.class)
    @GetMapping("/work-acts")
    ResponseEntity<List<WorkActViewDto>> getActs(WorkActDto filt) {
        filt.setUserId(getAuthUserId());
        return serv.getAll(filt);
    }

    @GetMapping("/workact/poss")
    ResponseEntity<List<WorkActPosViewDto>> getPoss(WorkActPosDto filt) {
        return serv.getPoss(filt);
    }


}
