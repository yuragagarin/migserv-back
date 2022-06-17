package ru.migplus.site.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.migplus.site.dto.consumer.ConsumerDto;
import ru.migplus.site.dto.consumer.ConsumerViewDto;
import ru.migplus.site.dto.consumer.Views;
import ru.migplus.site.service.ConsumerService;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class ConsumerController extends BaseController {

    private final ConsumerService serv;

    @Autowired
    public ConsumerController(ConsumerService service) {
        this.serv = service;
    }

    @GetMapping("/consumers/idn")
    @JsonView(Views.IdName.class)
        //@PreAuthorize("hasAuthority('admin') or hasAuthority('serviceman')")
        //@PreAuthorize("hasAuthority('consumers:dr')")
    ResponseEntity<List<ConsumerDto>> getIdName(ConsumerDto filt) {
        return serv.getAll(filt, getCurUserInfo());
    }

    @GetMapping("/consumers/withparams")
    @JsonView(Views.IdName.class)
    ResponseEntity<List<ConsumerViewDto>> getIdNameWithParams(ConsumerDto filter) {
        return serv.getAllWithParams(filter);
    }

    @GetMapping("/consumers/dict")
    @JsonView(Views.Dict.class)
        //@PreAuthorize("hasAuthority('consumers:dr')")
    ResponseEntity<List<ConsumerDto>> getDict(ConsumerDto filt) {
        return serv.getAll(filt, getCurUserInfo());
    }

    @GetMapping(value = "/consumer/{id}")
    public ResponseEntity<ConsumerDto> get(@PathVariable long id) {
        return serv.get(id);
    }

    @PostMapping(value = "/consumer")
    public ResponseEntity create(@RequestBody ConsumerDto dto) {

        dto.setUserId(getAuthUserId());

        return serv.create(dto);

    }

    @PutMapping(value = "/consumer")
    public ResponseEntity update(@RequestBody ConsumerDto dto) {

        dto.setUserId(getAuthUserId());

        return serv.update(dto);

    }

    @DeleteMapping("/consumer/{id}")
    public void deleteUser(@PathVariable long id) {

        ConsumerDto dto = new ConsumerDto();
        dto.setId(id);
        dto.setUserId(getAuthUserId());
        serv.delete(dto);

    }
}