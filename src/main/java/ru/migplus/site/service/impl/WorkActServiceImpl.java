package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.migplus.site.dao.main.repo.*;
import ru.migplus.site.domain.*;
import ru.migplus.site.dto.workAct.*;
import ru.migplus.site.exceptions.WorkActNotFoundException;
import ru.migplus.site.exceptions.WorkActOperConfirmedException;
import ru.migplus.site.exceptions.WorkActOperNoConfirmedException;
import ru.migplus.site.exceptions.WorkActPosTypeNotFoundException;
import ru.migplus.site.service.WorkActService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WorkActServiceImpl implements WorkActService {

    private final WorkActRepository repo;
    private final WorkActPosRepository workActPosRepo;
    private final WorkActPosTypeRepository workActPosTypeRepo;
    private final WorkActOperRepository workActOperRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper;


    @Autowired
    public WorkActServiceImpl(WorkActRepository repo, WorkActPosRepository workActPosRepo, WorkActPosTypeRepository workActPosTypeRepo
            , WorkActOperRepository workActOperRepo, UserRepository userRepo, ModelMapper mapper) {

        this.repo = repo;
        this.workActPosRepo = workActPosRepo;
        this.workActPosTypeRepo = workActPosTypeRepo;
        this.workActOperRepo = workActOperRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;

    }

    @Override
    public ResponseEntity<List<WorkActPosTypeDto>> getPosTypes() {

        List<WorkActPosType> items = workActPosTypeRepo.findAll();
        List<WorkActPosTypeDto> dtos = items
                .stream()
                .map(item -> mapper.map(item, WorkActPosTypeDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity create(WorkActDto dto) {

        WorkAct newItem = new WorkAct();
        Optional<WorkAct> item = repo.getFirstByOrderByIdDesc();

        if (item.isPresent()) {
            Integer num = Integer.parseInt(item.get().getNum()) + 1;
            newItem.setNum(num.toString());
        } else
            newItem.setNum("1");

        repo.save(newItem);

        //позиции
        for (WorkActPosDto it : dto.getPoss()) {
            WorkActPos newPos = new WorkActPos();
            Optional<WorkActPosType> type = workActPosTypeRepo.findFirstById(it.getTypeId());
            if (!type.isPresent()) throw new WorkActPosTypeNotFoundException();
            newPos.setType(type.get());

            newPos.setUnitId(it.getUnitId());
            newPos.setOperId(it.getOperId());

            newPos.setWorkAct(newItem);
            workActPosRepo.save(newPos);
        }

        User user = userRepo.getOne(dto.getUserId());

        WorkActOper oper = new WorkActOper();
        oper.setName("Создание");
        oper.setDateSt(LocalDateTime.now());
        oper.setWorkAct(newItem);
        oper.setUser(user);

        workActOperRepo.save(oper);

        return new ResponseEntity(HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<List<WorkActViewDto>> getAll(WorkActDto filt) {

        List<WorkActViewDto> dtos = null;

        if (filt == null) filt = new WorkActDto();
        Optional<User> user = userRepo.findById(filt.getUserId());

        if (user.get().getRoles().get(0).getName().equals("Бухгалтер"))
            dtos = repo.getAllAccauntant();
        else
            dtos = repo.getAllHeadman();

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<WorkActPosViewDto>> getPoss(WorkActPosDto filt) {

        List<WorkActPosViewDto> dtos = workActPosRepo.getAll(filt.getWorkActId(), filt.getUserId());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity confirm(WorkActDto dto) {

        LocalDateTime curDate = LocalDateTime.now();
        Optional<WorkAct> workAct = repo.findById(dto.getId());
        if (!workAct.isPresent()) throw new WorkActNotFoundException();

        Optional<User> user = userRepo.findById(dto.getUserId());

        if (user.get().getRoles().get(0).getName().equals("Начальник газовой службы")) {
            List<String> roles = new ArrayList<>();
            roles.add("Бухгалтер");
            WorkActOper oper = workActOperRepo.findByWorkActIdAndRoleName(workAct.get().getId(), roles, "Подтверждение");
            if (oper == null) throw new WorkActOperNoConfirmedException();
        }

        WorkActOper oper = workActOperRepo.findFirstByWorkActAndUserAndName(workAct.get(), user.get(), "Подтверждение");
        if (oper != null) throw new WorkActOperConfirmedException();

        oper = new WorkActOper();
        oper.setName("Подтверждение");
        oper.setWorkAct(workAct.get());
        oper.setDateSt(curDate);
        oper.setUser(user.get());

        workActOperRepo.save(oper);

        return new ResponseEntity(HttpStatus.OK);

    }

    @Override
    public ResponseEntity cancel(WorkActDto dto) {

        Optional<WorkAct> workAct = repo.findById(dto.getId());
        if (!workAct.isPresent()) throw new WorkActNotFoundException();

        User user = userRepo.getOne(dto.getUserId());

        WorkActOper oper = workActOperRepo.findFirstByWorkActAndUserAndName(workAct.get(), user, "Подтверждение");
        workActOperRepo.delete(oper);

        return new ResponseEntity(HttpStatus.OK);

    }


}