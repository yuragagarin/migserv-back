package ru.migplus.site.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.migplus.site.dto.user.ServAlarmDto;
import ru.migplus.site.service.StateService;

@Slf4j
@RestController
@RequestMapping("/api/")
public class StateController extends BaseController {

    private final StateService serv;

    @Autowired
    public StateController(StateService service) {
        this.serv = service;
    }


    @GetMapping(value = "/state/serv-alarm-statuses")
    public ResponseEntity<ServAlarmDto> getStatuses() {
        return serv.getStatuses(getCurUserInfo());
    }

}
