package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dto.consumer.ParamPerDayDto;
import ru.migplus.site.dto.consumer.ParamPerHourDto;
import ru.migplus.site.dto.rdParam.*;
import ru.migplus.site.security.services.UserDetailsImpl;

import java.util.List;

public interface RdParamService {
    ResponseEntity<List<SafeEventViewDto>> getSafeEvents(SafeEventDto dto);

    ResponseEntity<List<SafeParamStateDto>> getSafeParamStates(UserDetailsImpl userInfo);

    ResponseEntity repair(SafeParamStateRepairDto dto);

    ResponseEntity<SafeEventViewDto> forRepairCnt();

    ResponseEntity<List<SafeEventTimelineDto>> getTimeline(SafeEventDto dto);

    ResponseEntity<List<RdParamDto>> getAll(RdParamDto filt);

    ResponseEntity<List<ParamPerHourDto>> getParamsPerHour(ParamPerHourDto filt);

    ResponseEntity<List<ParamPerHourDto>> getParamsPerLastHour(ParamPerHourDto filt);

    ResponseEntity<List<ParamPerHourDto>> getParamsPerLastHourAll(ParamPerHourDto filt);

    ResponseEntity<List<ParamPerDayDto>> getParamsPerDay(ParamPerDayDto filt);
}
