package ru.migplus.site.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.migplus.site.domain.EnvUnit;
import ru.migplus.site.dto.envUnit.*;
import ru.migplus.site.security.services.UserDetailsImpl;
import ru.migplus.site.service.EnvUnitService;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class EnvUnitController extends BaseController {

    private final EnvUnitService serv;

    @Autowired
    public EnvUnitController(EnvUnitService service) {
        this.serv = service;
    }

    @GetMapping("/envunits/idnn")
    @JsonView(Views.IdNameNumber.class)
    ResponseEntity<List<EnvUnitDto>> getIdNameNumber(EnvUnitDto filter) {
        return serv.getAll(filter);
    }

    @GetMapping(value = "/envunits/income")
    ResponseEntity<List<EnvUnitViewDto>> getIncomes(EnvUnitDto filt) {
        return serv.getIncomes(filt);
    }

    @PostMapping(value = "/envunit")
    public ResponseEntity create(@RequestBody EnvUnitDto dto) {

        dto.setUserId(getAuthUserId());

        return serv.create(dto);

    }

    @DeleteMapping("/envunit/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        EnvUnitDto dto = new EnvUnitDto();
        dto.setId(id);
        dto.setUserId(getAuthUserId());

        return serv.delete(dto);

    }

    @GetMapping(value = "/envunits/setup")
    ResponseEntity<List<EnvUnitViewDto>> getSetups(EnvUnitDto filt) {
        return serv.getSetups(filt, getCurUserInfo());
    }

    @GetMapping(value = "/envunits/change")
    ResponseEntity<List<EnvUnitViewDto>> getChanges(EnvUnitDto filt) {
        return serv.getChanges(filt, getCurUserInfo());
    }

    @PutMapping(value = "/envunit/setup/{id}")
    public ResponseEntity setup(@PathVariable Long id, @RequestBody EnvUnitDto envUnit) {

        envUnit.setUserId(getAuthUserId());

        return serv.setup(id, envUnit);

    }

    @PutMapping(value = "/envunit/change/{id}")
    public ResponseEntity<EnvUnit> change(@PathVariable Long id, @RequestBody EnvUnitDto envUnit) {

        envUnit.setUserId(getAuthUserId());

        return serv.change(id, envUnit);

    }

    @GetMapping("/envunits/change/hist")
    ResponseEntity<List<EnvUnitViewDto>> getAllChanges(EnvUnitDto filt) {
        return serv.getAllChanges(filt, getCurUserInfo());
    }

    @GetMapping(value = "/envunit/statuses")
    public ResponseEntity<EnvUnitStatusesDto> getStatuses() {
        return serv.getStatuses(getAuthUserId());
    }

    @GetMapping(value = "/envunits/verify")
    ResponseEntity<List<EnvUnitViewDto>> getVerifications(EnvUnitDto filt) {
        return serv.getVerifications(filt, getCurUserInfo());
    }

    @GetMapping(value = "/envunits/verify/hist")
    ResponseEntity<List<EnvUnitViewDto>> getVerificationsHist(EnvUnitDto filt) {
        return serv.getAllVerifications(filt, getCurUserInfo());
    }

    @PutMapping(value = "/envunit/verify/{id}")
    public ResponseEntity<EnvUnit> verify(@PathVariable Long id, @RequestBody EnvUnitDto envUnit) {

        envUnit.setUserId(getAuthUserId());

        return serv.verify(id, envUnit);

    }

    @GetMapping(value = "/envunits/stat/installed-in-month-last-12-monthes")
    public ResponseEntity<List<EnvUnitViewStatDto>> getInstalledInMonthLast12Monthes() {

        return serv.getInstalledInMonthLast12Monthes();

    }

    @GetMapping(value = "/envunits/stat/changed-in-month-last-12-monthes")
    public ResponseEntity<List<EnvUnitViewStatDto>> getChangedInMonthLast12Monthes() {

        return serv.getInstalledInMonthLast12Monthes();

    }

    @GetMapping(value = "/envunits/stat/verify-in-month-last-12-monthes")
    public ResponseEntity<List<EnvUnitViewStatDto>> getVerifyInMonthLast12Monthes() {

        return serv.getInstalledInMonthLast12Monthes();

    }

    @GetMapping(value = "/envunits/stat/changed-expired-in-month-last-12-monthes")
    public ResponseEntity<List<EnvUnitViewStatDto>> getChangedExpiredInMonthLast12Monthes() {

        return serv.getChangedExpiredInMonthLast12Monthes();

    }
}