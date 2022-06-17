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
import ru.migplus.site.dto.envUnitOper.EnvUnitOperDto;
import ru.migplus.site.exceptions.*;
import ru.migplus.site.service.EnvUnitOperService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EnvUnitOperServiceImpl implements EnvUnitOperService {

    private final EnvUnitOperRepository repo;
    private final EnvUnitRepository envUnitRepo;
    private final EnvUnitOperNameRepository envUnitOperNameRepo;
    private final ModelMapper mapper;

    @Autowired
    public EnvUnitOperServiceImpl(EnvUnitOperRepository repo, EnvUnitRepository envUnitRepo
            , EnvUnitOperNameRepository envUnitOperNameRepo, ModelMapper mapper) {

        this.repo = repo;
        this.envUnitOperNameRepo = envUnitOperNameRepo;
        this.envUnitRepo = envUnitRepo;
        this.mapper = mapper;

    }

    @Override
    @Transactional
    public ResponseEntity delete(EnvUnitOperDto dto) {

        EnvUnitOper item = repo.getOne(dto.getId());
        if (item == null) {
            throw new EnvUnitOperNotFoundException();
        }

        switch (item.getName().getName()) {
            case ("Установка"):
                if (item.getRefEnvUnitOper() != null) throw new EnvUnitOperException();
                installCancel(item);
                break;
            case ("Снятие"):
                removeCancel(item);
                break;
            case ("Поверка"):
                verifyCancel(item);
                break;
        }

        return new ResponseEntity(HttpStatus.OK);

    }

    private void installCancel(EnvUnitOper item) {

        Optional<EnvUnitOper> itemOperNext = repo.findFirstByEnvUnitAndDateStGreaterThan(item.getEnvUnit(), item.getDateSt());
        if (itemOperNext.isPresent()) {
            throw new EnvUnitOperException();
        }

        Optional<EnvUnitOper> prevOper = repo.findFirstByEnvUnitAndDateStLessThanOrderByDateStDesc(item.getEnvUnit(), item.getDateSt());
        if (prevOper.isPresent()) {
            prevOper.get().setDateFn(null);
            repo.save(prevOper.get());
        }

        repo.delete(item);

    }

    private void removeCancel(EnvUnitOper item) {

        EnvUnitOper refOper = repo.getOne(item.getRefEnvUnitOper().getId());
        if (refOper != null) {
            installCancel(refOper);
        }

        repo.delete(item);

        Optional<EnvUnitOper> prevOper = repo.findFirstByEnvUnitAndDateStLessThanOrderByDateStDesc(item.getEnvUnit(), item.getDateSt());
        if (prevOper.isPresent()) {
            prevOper.get().setDateFn(null);
            repo.save(prevOper.get());
        }

    }

    private void verifyCancel(EnvUnitOper item) {

        EnvUnitOper curOper = repo.getOne(item.getId());
        if (curOper == null)
            throw new EnvUnitOperNotFoundException();

        Optional<EnvUnitOperName> operName = envUnitOperNameRepo.findFirstByName("Поверка");
        if (!operName.isPresent()) {
            throw new EnvUnitOperNameNotFoundException();
        }

        Optional<EnvUnitOper> itemOperNext = repo.findFirstByEnvUnitAndNameEqualsAndDateFnNotNullAndDateStGreaterThan(item.getEnvUnit(), operName.get(), curOper.getDateSt());
        if (itemOperNext.isPresent())
            throw new EnvUnitOperException();

        repo.delete(item);

    }

}
