package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.migplus.site.dao.main.repo.*;
import ru.migplus.site.domain.*;
import ru.migplus.site.dto.equip.EquipAddDto;
import ru.migplus.site.dto.equip.EquipDto;
import ru.migplus.site.dto.equip.EquipViewDto;
import ru.migplus.site.dto.equipServ.EquipServDto;
import ru.migplus.site.dto.equipServ.EquipServHistViewDto;
import ru.migplus.site.dto.equipServ.EquipServStatusesDto;
import ru.migplus.site.dto.equipServ.EquipServViewDto;
import ru.migplus.site.exceptions.ConsumerNotFoundException;
import ru.migplus.site.exceptions.EquipNotFoundException;
import ru.migplus.site.exceptions.EquipOperNotFoundException;
import ru.migplus.site.exceptions.UserNotFoundException;
import ru.migplus.site.service.EquipService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EquipServiceImpl implements EquipService {

    private final EquipRepository repo;
    private final EquipServRepository servRepo;
    private final UserRepository userRepo;
    private final EquipOperRepository equipOperRepo;
    private final ConsumerRepository consumerRepo;
    private final ModelMapper mapper;

    @Autowired
    public EquipServiceImpl(EquipRepository repo, EquipServRepository servRepo, UserRepository userRepo, EquipOperRepository equipOperRepo, ConsumerRepository consumerRepo, ModelMapper mapper) {

        this.repo = repo;
        this.servRepo = servRepo;
        this.userRepo = userRepo;
        this.equipOperRepo = equipOperRepo;
        this.consumerRepo = consumerRepo;
        this.mapper = mapper;

    }

    @Override
    public ResponseEntity<List<EquipDto>> getIdName() {

        List<Equip> items = repo.findAll();
        List<EquipDto> dtos = items
                .stream()
                .map(item -> mapper.map(item, EquipDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<EquipViewDto>> getAll() {

        List<EquipViewDto> equips = repo.getAll();

        return new ResponseEntity<List<EquipViewDto>>(equips, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<EquipServViewDto>> getServs(EquipServDto filt) {

        List<EquipServViewDto> equipServs = servRepo.getServs(filt.getConsumerId(), filt.getCode(), filt.getName(), filt.getNumber(), filt.getOperName());

        return new ResponseEntity<>(equipServs, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<EquipServ> perform(Long equipOperId, EquipServDto itemDto) {

        LocalDateTime curDate = LocalDateTime.now();

        Optional<User> user = userRepo.findById(itemDto.getUserId());
        if (!user.isPresent()) throw new UserNotFoundException();

        Optional<EquipOper> equipOper = equipOperRepo.findById(equipOperId);
        if (!equipOper.isPresent()) throw new EquipOperNotFoundException();

        /*Optional<EquipServ> equipServ = servRepo.findFirstByEquipOperIdOrderByIdDesc(equipOper.get().getId());
        if (!equipServ.isPresent()) throw new EquipServNotFoundException();*/

        Optional<EquipServ> equipServ = servRepo.findFirstByDateFnIsNullAndEquipOperId(equipOper.get().getId());
        if (equipServ.isPresent()) {
            equipServ.get().setDateFn(curDate);
            servRepo.save(equipServ.get());
        }

        EquipServ equipServNew = new EquipServ();
        equipServNew.setDateSt(curDate);
        equipServNew.setNextDate(itemDto.getNextDate());
        equipServNew.setEquipOper(equipOper.get());
        equipServNew.setUser(user.get());
        servRepo.save(equipServNew);

        return new ResponseEntity(HttpStatus.OK);

    }

    @Override
    public ResponseEntity plan(Long equipOperId, EquipServDto itemDto) {

        Optional<User> user = userRepo.findById(itemDto.getUserId());
        if (!user.isPresent()) throw new UserNotFoundException();

        Optional<EquipOper> equipOper = equipOperRepo.findById(equipOperId);
        if (!equipOper.isPresent()) throw new EquipOperNotFoundException();

        EquipServ equipServNew = new EquipServ();
        equipServNew.setNextDate(itemDto.getNextDate());
        equipServNew.setEquipOper(equipOper.get());
        equipServNew.setUser(user.get());
        servRepo.save(equipServNew);

        return null;
    }

    @Override
    public ResponseEntity<EquipServStatusesDto> getStatuses() {

        return new ResponseEntity<>(servRepo.getStatuses(), new HttpHeaders(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<EquipServHistViewDto>> getHists(EquipServDto equipServDto) {

        List<EquipServHistViewDto> equipServs = servRepo.getServHists(equipServDto.getConsumerId());

        return new ResponseEntity<>(equipServs, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<EquipAddDto> add(EquipAddDto dto) {

        Equip item = new Equip();
        Optional<Consumer> consumer = consumerRepo.findById(dto.getConsumerId());
        if (consumer.isPresent()) {
            item.setConsumer(consumer.get());
        } else {
            throw new ConsumerNotFoundException();
        }
        item.setName(dto.getName());
        item.setNumber(dto.getNumber());
        item.setYear(dto.getYear());
        repo.saveAndFlush(item);

        List<EquipOper> equipOpers = new ArrayList() {{
            for (String equipOper : dto.getEquipOpers()) {
                add(new EquipOper(equipOper, item));
            }
        }};
        equipOperRepo.saveAll(equipOpers);

        return new ResponseEntity<>(dto, new HttpHeaders(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity delete(long id) {

        Optional<Equip> item = repo.findById(id);
        item.orElseThrow(() -> new EquipNotFoundException()).setStatus(Status.DELETED);
        repo.save(item.get());

        return new ResponseEntity<EquipAddDto>(new HttpHeaders(), HttpStatus.OK);

    }

}