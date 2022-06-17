package ru.migplus.site.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.migplus.site.dto.consumer.ParamPerDayDto;
import ru.migplus.site.dto.consumer.ParamPerHourDto;
import ru.migplus.site.dto.rdParam.*;
import ru.migplus.site.service.RdParamService;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class RdParamController extends BaseController {

    private final RdParamService serv;

    @Autowired
    public RdParamController(RdParamService service) {
        this.serv = service;
    }

    @GetMapping("/rdparams/idn")
    @JsonView(Views.IdName.class)
    ResponseEntity<List<RdParamDto>> getIdName(RdParamDto filt) {
        return serv.getAll(filt);
    }

    @PostMapping("/safe-params/hist")
        //@PreAuthorize("hasAuthority('safe_events:r')")
    ResponseEntity<List<SafeEventViewDto>> getHist(@RequestBody SafeEventDto dto) {
        return serv.getSafeEvents(dto);
    }

    @GetMapping("/safe-param/states")
        //@PreAuthorize("hasAuthority('safe-param-states:r')")
    ResponseEntity<List<SafeParamStateDto>> getParamStates(SafeParamStateDto filt) {
        return serv.getSafeParamStates(getCurUserInfo());
    }

    @PostMapping(value = "/safe-param-states/repair")
        //@PreAuthorize("hasAuthority('safe-param-states:repair')")
    ResponseEntity repair(@RequestBody SafeParamStateRepairDto dto) {

        dto.setUserId(getAuthUserId());

        return serv.repair(dto);

    }

    @GetMapping("/safe-events-for-repair/cnt")
    @JsonView(Views.ForRepairCnt.class)
    ResponseEntity<SafeEventViewDto> safeEventsForRepairCnt() {
        return serv.forRepairCnt();
    }

    @GetMapping("/safe-events/timeline")
        //@PreAuthorize("hasAuthority('safe_events:r')")
    ResponseEntity<List<SafeEventTimelineDto>> getSafeEventTimeline(SafeEventDto dto) {
        return serv.getTimeline(dto);
    }

    @GetMapping("/rdparams/params-per-day")
    @JsonView(ru.migplus.site.dto.consumer.Views.NameValueHour.class)
    ResponseEntity<List<ParamPerDayDto>> getParamsByDay(ParamPerDayDto filt) {
        return serv.getParamsPerDay(filt);
    }

    @GetMapping("/rdparams/params-per-hour")
    @JsonView(ru.migplus.site.dto.consumer.Views.NameValueMinute.class)
    ResponseEntity<List<ParamPerHourDto>> getParamsByHour(ParamPerHourDto filt) {
        return serv.getParamsPerHour(filt);
    }

    @GetMapping("/rdparams/params-per-last-hour")
    @JsonView(ru.migplus.site.dto.consumer.Views.NameValueMinute.class)
    ResponseEntity<List<ParamPerHourDto>> getParamsByLastHour(ParamPerHourDto filt) {
        return serv.getParamsPerLastHour(filt);
    }

    @GetMapping("/rdparams/params-per-last-hour-all")
    @JsonView(ru.migplus.site.dto.consumer.Views.NameValueMinute.class)
    ResponseEntity<List<ParamPerHourDto>> getParamsByLastHourAll(ParamPerHourDto filt) {
        return serv.getParamsPerLastHourAll(filt);
    }

}
