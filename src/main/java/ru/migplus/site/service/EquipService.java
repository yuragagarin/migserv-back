package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dto.equip.EquipAddDto;
import ru.migplus.site.dto.equip.EquipDto;
import ru.migplus.site.dto.equip.EquipViewDto;
import ru.migplus.site.dto.equipServ.EquipServDto;
import ru.migplus.site.dto.equipServ.EquipServHistViewDto;
import ru.migplus.site.dto.equipServ.EquipServStatusesDto;
import ru.migplus.site.dto.equipServ.EquipServViewDto;

import java.util.List;

public interface EquipService {
    ResponseEntity<List<EquipDto>> getIdName();

    ResponseEntity<List<EquipViewDto>> getAll();

    ResponseEntity<List<EquipServViewDto>> getServs(EquipServDto dto);

    ResponseEntity perform(Long id, EquipServDto dto);

    ResponseEntity plan(Long id, EquipServDto dto);

    ResponseEntity<EquipServStatusesDto> getStatuses();

    ResponseEntity<List<EquipServHistViewDto>> getHists(EquipServDto equipServDto);

    ResponseEntity<EquipAddDto> add(EquipAddDto dto);

    ResponseEntity delete(long id);
}
