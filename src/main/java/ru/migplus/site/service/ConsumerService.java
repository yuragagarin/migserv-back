package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dao.main.filter.ConsumerSpec;
import ru.migplus.site.dto.consumer.ConsumerDto;
import ru.migplus.site.dto.consumer.ConsumerViewDto;
import ru.migplus.site.security.services.UserDetailsImpl;

import java.util.List;

public interface ConsumerService {
    ResponseEntity<List<ConsumerDto>> getAll(ConsumerDto filt, UserDetailsImpl userInfo);

    ResponseEntity<List<ConsumerViewDto>> getAllWithParams(ConsumerDto filt);

    ResponseEntity create(ConsumerDto dto);

    ResponseEntity delete(ConsumerDto dto);

    ResponseEntity<ConsumerDto> get(long id);

    ResponseEntity update(ConsumerDto dto);
}
