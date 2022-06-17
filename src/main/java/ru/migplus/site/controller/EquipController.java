package ru.migplus.site.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.migplus.site.dto.equip.EquipAddDto;
import ru.migplus.site.dto.equip.EquipDto;
import ru.migplus.site.dto.equip.EquipViewDto;
import ru.migplus.site.dto.equipServ.EquipServDto;
import ru.migplus.site.dto.equipServ.EquipServHistViewDto;
import ru.migplus.site.dto.equipServ.EquipServStatusesDto;
import ru.migplus.site.dto.equipServ.EquipServViewDto;
import ru.migplus.site.service.EquipService;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class EquipController extends BaseController {

    private final EquipService serv;

    @Autowired
    public EquipController(EquipService service) {
        this.serv = service;
    }

    @GetMapping("/equipsid")
        //@PreAuthorize("hasAuthority('admin') or hasAuthority('serviceman')")
    ResponseEntity<List<EquipDto>> getIdName() {
        return serv.getIdName();
    }

    @GetMapping("/equips")
    ResponseEntity<List<EquipViewDto>> getAll() {
        return serv.getAll();
    }

    @GetMapping(value = "/equips/serv")
    ResponseEntity<List<EquipServViewDto>> getServs(EquipServDto filt) {
        return serv.getServs(filt);
    }

    @PutMapping(value = "/equip/serv/perform/{id}")
    ResponseEntity<List<EquipServViewDto>> perform(@PathVariable Long id, @RequestBody EquipServDto dto) {

        dto.setUserId(getAuthUserId());

        return serv.perform(id, dto);

    }

    @PutMapping(value = "/equip/serv/plan/{id}")
    ResponseEntity<List<EquipServViewDto>> plan(@PathVariable Long id, @RequestBody EquipServDto dto) {

        dto.setUserId(getAuthUserId());

        return serv.plan(id, dto);

    }

    @GetMapping(value = "/equip/statuses")
    public ResponseEntity<EquipServStatusesDto> getStatuses() {
        return serv.getStatuses();
    }

    @GetMapping(value = "/equips/serv/hist")
    ResponseEntity<List<EquipServHistViewDto>> getHists(EquipServDto dto) {
        return serv.getHists(dto);
    }

    @PostMapping(value = "/equip")
    ResponseEntity<EquipAddDto> add(@RequestBody EquipAddDto dto) {
        return serv.add(dto);
    }

    @DeleteMapping("/equip/{id}")
    public void deleteEquip(@PathVariable long id) {
        serv.delete(id);
    }

}
