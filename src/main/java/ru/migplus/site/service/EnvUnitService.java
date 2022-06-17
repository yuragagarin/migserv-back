package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.domain.EnvUnit;
import ru.migplus.site.dto.envUnit.*;
import ru.migplus.site.security.services.UserDetailsImpl;

import java.util.List;

public interface EnvUnitService {
    ResponseEntity<List<EnvUnitViewDto>> getIncomes(EnvUnitDto filt);

    ResponseEntity<List<EnvUnitViewDto>> getSetups(EnvUnitDto filt, UserDetailsImpl userInfo);

    ResponseEntity setup(Long id, EnvUnitDto dto);

    ResponseEntity<List<EnvUnitViewDto>> getChanges(EnvUnitDto dto, UserDetailsImpl userInfo);

    ResponseEntity<EnvUnit> change(Long srcId, EnvUnitDto dto);

    ResponseEntity<List<EnvUnitViewDto>> getAllChanges(EnvUnitDto filt, UserDetailsImpl userInfo);

    ResponseEntity<EnvUnitStatusesDto> getStatuses(long userId);

    ResponseEntity<List<EnvUnitViewDto>> getVerifications(EnvUnitDto dto, UserDetailsImpl userInfo);

    ResponseEntity<EnvUnit> verify(Long id, EnvUnitDto dto);

    ResponseEntity create(EnvUnitDto dto);

    ResponseEntity delete(EnvUnitDto dto);

    ResponseEntity deleteOper(EnvUnitDto dto);

    ResponseEntity<List<EnvUnitDto>> getAll(EnvUnitDto filter);

    ResponseEntity<List<EnvUnitViewDto>> getAllVerifications(EnvUnitDto filt, UserDetailsImpl userInfo);

    ResponseEntity<List<EnvUnitViewStatDto>> getInstalledInMonthLast12Monthes();

    ResponseEntity<List<EnvUnitViewStatDto>> getChangedInMonthLast12Monthes();

    ResponseEntity<List<EnvUnitViewStatDto>> getVerifyInMonthLast12Monthes();

    ResponseEntity<List<EnvUnitViewStatDto>> getChangedExpiredInMonthLast12Monthes();
}
