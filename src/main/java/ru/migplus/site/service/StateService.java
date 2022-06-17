package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dto.user.ServAlarmDto;
import ru.migplus.site.security.services.UserDetailsImpl;

public interface StateService {

    ResponseEntity<ServAlarmDto> getStatuses(UserDetailsImpl userInfo);
}
