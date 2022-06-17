package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.migplus.site.dao.main.repo.RdParamJdbcRepository;
import ru.migplus.site.dao.main.repo.RdParamRepository;
import ru.migplus.site.dao.main.repo.SafeEventRepository;
import ru.migplus.site.dao.main.repo.UserRepository;
import ru.migplus.site.domain.SafeEvent;
import ru.migplus.site.domain.User;
import ru.migplus.site.dto.consumer.ParamPerDayDto;
import ru.migplus.site.dto.consumer.ParamPerHourDto;
import ru.migplus.site.dto.rdParam.*;
import ru.migplus.site.exceptions.SafeEventNotFoundException;
import ru.migplus.site.security.services.UserDetailsImpl;
import ru.migplus.site.service.RdParamService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RdParamServiceImpl implements RdParamService {

    private final UserRepository userRepo;
    private final SafeEventRepository repo;
    private final RdParamJdbcRepository rdParamJdbcRepo;
    private final RdParamRepository rdParamRepo;
    private final ModelMapper mapper;

    @Autowired
    public RdParamServiceImpl(UserRepository userRepo, SafeEventRepository repo, RdParamJdbcRepository rdParamJdbcRepo, RdParamRepository rdParamRepo, ModelMapper mapper) {

        this.userRepo = userRepo;
        this.repo = repo;
        this.rdParamJdbcRepo = rdParamJdbcRepo;
        this.rdParamRepo = rdParamRepo;
        this.mapper = mapper;

    }

    @Override
    public ResponseEntity<List<SafeEventViewDto>> getSafeEvents(SafeEventDto dto) {

        List<SafeEventViewDto> dtos = repo.findByConsumerId(dto.getConsumerId());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<SafeParamStateDto>> getSafeParamStates(UserDetailsImpl userInfo) {
        Long userId = Long.valueOf(0);
        Long consumerId = Long.valueOf(0);

        if (userInfo != null) {
            if (userInfo.getRoles().contains("Инженер-наладчик газ. обор-я") ||
                    userInfo.getRoles().contains("Инженер-наладчик кот. обор-я")) {
                userId = userInfo.getId();
            }

            if (userInfo.getRoles().contains("Клиент")) {
                consumerId = userInfo.getConsumerId();
            }

        }
        List<SafeParamStateDto> dtos = repo.getSafeParamStates(consumerId, userId);

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity repair(SafeParamStateRepairDto itemDto) {

        SafeEvent item = repo.getOne(itemDto.getSafeEventId());
        if (item == null) throw new SafeEventNotFoundException();

        item.setFnDate(itemDto.getRepairDate());

        repo.saveAndFlush(item);

        return new ResponseEntity(new HttpHeaders(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<SafeEventViewDto> forRepairCnt() {

        SafeEventViewDto res = repo.findForRepairCnt();

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<SafeEventTimelineDto>> getTimeline(SafeEventDto dto) {

        return new ResponseEntity<>(repo.getTimelines(dto.getConsumerId()), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<RdParamDto>> getAll(RdParamDto filt) {

        return new ResponseEntity<>(rdParamJdbcRepo.getRdParams(filt.getConsumerId()), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<ParamPerHourDto>> getParamsPerHour(ParamPerHourDto dto) {

        String consumerTable = "rd_consumer_" + dto.getConsumerId();
        if (!rdParamRepo.tableExists(consumerTable)) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        return new ResponseEntity<>(rdParamJdbcRepo.getParamsPerHour(dto), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<ParamPerHourDto>> getParamsPerLastHour(ParamPerHourDto filt) {

        String consumerTable = "rd_consumer_" + filt.getConsumerId();
        if (!rdParamRepo.tableExists(consumerTable)) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        return new ResponseEntity<>(rdParamJdbcRepo.getParamsPerLastHour(filt), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<ParamPerHourDto>> getParamsPerLastHourAll(ParamPerHourDto filt) {

        String consumerTable = "rd_consumer_" + filt.getConsumerId();
        if (!rdParamRepo.tableExists(consumerTable)) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        return new ResponseEntity<>(rdParamJdbcRepo.getParamsPerLastHourAll(filt), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<ParamPerDayDto>> getParamsPerDay(ParamPerDayDto filt) {

        String consumerTable = "rd_consumer_" + filt.getConsumerId();
        if (!rdParamRepo.tableExists(consumerTable)) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        return new ResponseEntity<>(rdParamJdbcRepo.getParamsPerDay(filt), HttpStatus.OK);

    }
}
