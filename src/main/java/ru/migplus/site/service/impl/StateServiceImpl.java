package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.migplus.site.dao.main.repo.StateRepository;
import ru.migplus.site.dto.user.ServAlarmDto;
import ru.migplus.site.security.services.UserDetailsImpl;
import ru.migplus.site.service.StateService;

@Slf4j
@Service

public class StateServiceImpl implements StateService {

    private final StateRepository repo;


    @Autowired
    public StateServiceImpl(StateRepository repo) {
        this.repo = repo;
    }


    @Override
    public ResponseEntity<ServAlarmDto> getStatuses(UserDetailsImpl userInfo) {

        Long userId = Long.valueOf(0);

        if (userInfo != null)
            if (!userInfo.getRoles().contains("Начальник сервисной службы") &&
                    !userInfo.getRoles().contains("Начальник газовой службы")) {
                userId = userInfo.getId();
            }

        return new ResponseEntity<>(repo.getServAlarmStatuses(userId), HttpStatus.OK);

    }
}
