package ru.migplus.site.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.migplus.site.dao.main.repo.*;
import ru.migplus.site.domain.*;
import ru.migplus.site.dto.envUnit.EnvUnitDto;
import ru.migplus.site.dto.envUnit.EnvUnitStatusesDto;
import ru.migplus.site.dto.envUnit.EnvUnitViewDto;
import ru.migplus.site.dto.envUnit.EnvUnitViewStatDto;
import ru.migplus.site.exceptions.*;
import ru.migplus.site.security.services.UserDetailsImpl;
import ru.migplus.site.service.EnvUnitService;
import ru.migplus.site.utils.MyBeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnvUnitServiceImpl implements EnvUnitService {

    private final EnvUnitRepository repo;
    private final ConsumerRepository consumerRepo;
    private final EnvUnitOperRepository envUnitOperRepo;
    private final EnvUnitOperNameRepository envUnitOperNameRepo;
    private final UserRepository userRepo;
    private final EquipRepository equipRepo;
    private final EnvUnitTypeRepository envUnitTypeRepo;
    private final ModelMapper mapper;

    @Autowired
    public EnvUnitServiceImpl(EnvUnitRepository repo, ConsumerRepository consumerRepo, EnvUnitOperRepository envUnitOperRepo,
                              EnvUnitOperNameRepository envUnitOperNameRepo, UserRepository userRepo, EquipRepository equipRepo,
                              EnvUnitTypeRepository envUnitTypeRepo, ModelMapper mapper) {

        this.repo = repo;
        this.consumerRepo = consumerRepo;
        this.envUnitOperRepo = envUnitOperRepo;
        this.envUnitOperNameRepo = envUnitOperNameRepo;
        this.userRepo = userRepo;
        this.equipRepo = equipRepo;
        this.envUnitTypeRepo = envUnitTypeRepo;
        this.mapper = mapper;

    }

    @Override
    public ResponseEntity<List<EnvUnitViewDto>> getIncomes(EnvUnitDto filt) {

        List<EnvUnitViewDto> envUnits = repo.getIncomes(filt.getCode(), filt.getNumber(), filt.getSerialNum(),
                filt.getPassport(), filt.getEnvUnitCategory());

        return new ResponseEntity<>(envUnits, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<EnvUnitViewDto>> getSetups(EnvUnitDto filt, UserDetailsImpl userInfo) {
        Long userId = Long.valueOf(0);
        if (userInfo != null) {
            if (userInfo.getRoles().contains("Инженер-наладчик газ. обор-я") ||
                    userInfo.getRoles().contains("Инженер-наладчик кот. обор-я")) {
                userId = userInfo.getId();
            }
        }
        List<EnvUnitViewDto> envUnits = repo.getSetups(userId, filt.getConsumerId());
        return new ResponseEntity<>(envUnits, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity setup(Long id, EnvUnitDto dto) {

        LocalDateTime curDateTime = LocalDateTime.now();
        EnvUnit item = findItemIfExists(id);
        MyBeanUtils.copyNonNullProperties(dto, item);
        item.setId(id);
        Optional<Consumer> consumer = consumerRepo.findById(dto.getConsumerId());
        if (consumer.isPresent()) {
            item.setConsumer(consumer.get());
        } else {
            throw new ConsumerNotFoundException();
        }
        Optional<Equip> equip = equipRepo.findById(dto.getEquipId());
        if (equip.isPresent()) {
            item.setEquip(equip.get());
        } else {
            throw new EquipNotFoundException();
        }

        dto.setInstallDate(curDateTime);

        LocalDateTime expirationDate = dto.getInstallDate().plusMonths(item.getWorkTime());

        closeLastOper(item, "Поступление", curDateTime);
        addEnvUnitOper(item, "Установка", dto.getInstallDate(), dto.getUserId(), expirationDate);

        if (item.getEnvUnitType().getCategory().equals("Прибор"))
            addEnvUnitOper(item, "Поверка", dto.getInstallDate(), dto.getUserId(), dto.getVerifyingDate());

        repo.saveAndFlush(item);

        return new ResponseEntity(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<EnvUnitViewDto>> getChanges(EnvUnitDto filt, UserDetailsImpl userInfo) {

        Long userId = Long.valueOf(0);
        if (userInfo != null) {
            if (userInfo.getRoles().contains("Инженер-наладчик газ. обор-я") ||
                    userInfo.getRoles().contains("Инженер-наладчик кот. обор-я")) {
                userId = userInfo.getId();
            }
        }

        List<EnvUnitViewDto> envUnits = repo.getChanges(filt.getCode(), filt.getNumber(), filt.getSerialNum(), filt.getPassport(),
                filt.getConsumerId(), userId);
        return new ResponseEntity<>(envUnits, HttpStatus.OK);

    }

    @Override
    public ResponseEntity change(Long srcId, EnvUnitDto dto) {

        LocalDateTime curDateTime = LocalDateTime.now();
        EnvUnit itemSrc = findItemIfExists(srcId);
        closeLastOper(itemSrc, "Установка", curDateTime);
        EnvUnitOper itemSrcRemovalOper = addEnvUnitOper(itemSrc, "Снятие", curDateTime.plusSeconds(1), dto.getUserId(), null);

        EnvUnit itemDst = findItemIfExists(dto.getId());

        itemDst.setConsumer(itemSrc.getConsumer());
        itemDst.setEquip(itemSrc.getEquip());

        closeLastOper(itemDst, "Поступление", curDateTime);
        LocalDateTime expirationDate = curDateTime.plusMonths(itemSrc.getWorkTime());
        EnvUnitOper itemDstInstallOper = addEnvUnitOper(itemDst, "Установка", curDateTime, dto.getUserId(), expirationDate);

        itemSrcRemovalOper.setRefEnvUnitOper(itemDstInstallOper);
        envUnitOperRepo.save(itemSrcRemovalOper);

        if (itemSrc.getEnvUnitType().getCategory().equals("Прибор"))
            addEnvUnitOper(itemDst, "Поверка", curDateTime, dto.getUserId(), dto.getVerifyingDate());

        repo.saveAndFlush(itemDst);

        return new ResponseEntity(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<EnvUnitViewDto>> getAllChanges(EnvUnitDto filt, UserDetailsImpl userInfo) {

        Long userId = Long.valueOf(0);
        if (userInfo != null) {
            if (userInfo.getRoles().contains("Инженер-наладчик газ. обор-я") ||
                    userInfo.getRoles().contains("Инженер-наладчик кот. обор-я")) {
                userId = userInfo.getId();
            }
        }

        List<EnvUnitViewDto> envUnits = repo.getHistChanges(filt.getCode(), filt.getNumber(), filt.getSerialNum(),
                filt.getPassport(), filt.getConsumerId(), userId);

        return new ResponseEntity<>(envUnits, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<EnvUnitStatusesDto> getStatuses(long userId) {

        return new ResponseEntity<>(repo.getStatuses(userId), new HttpHeaders(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<EnvUnitViewDto>> getVerifications(EnvUnitDto filt, UserDetailsImpl userInfo) {

        Long userId = Long.valueOf(0);
        if (userInfo != null) {
            if (userInfo.getRoles().contains("Инженер-наладчик газ. обор-я") ||
                    userInfo.getRoles().contains("Инженер-наладчик кот. обор-я")) {
                userId = userInfo.getId();
            }
        }

        List<EnvUnitViewDto> envUnits = repo.getVerifications(filt.getCode(), filt.getNumber(), filt.getSerialNum(),
                filt.getPassport(), filt.getConsumerId(), userId);

        return new ResponseEntity<>(envUnits, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<EnvUnitViewDto>> getAllVerifications(EnvUnitDto dto, UserDetailsImpl userInfo) {

        Long userId = Long.valueOf(0);
        if (userInfo != null) {
            if (userInfo.getRoles().contains("Инженер-наладчик газ. обор-я") ||
                    userInfo.getRoles().contains("Инженер-наладчик кот. обор-я")) {
                userId = userInfo.getId();
            }
        }

        List<EnvUnitViewDto> envUnits = repo.getHistVerifications(dto.getConsumerId(), userId);

        return new ResponseEntity<>(envUnits, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<EnvUnit> verify(Long id, EnvUnitDto dto) {

        EnvUnit item = findItemIfExists(id);
        LocalDateTime curDateTime = LocalDateTime.now();
        closeLastOper(item, "Поверка", curDateTime);
        addEnvUnitOper(item, "Поверка", curDateTime, dto.getUserId(), dto.getVerifyingDate());

        return new ResponseEntity<>(item, HttpStatus.OK);

    }

    @Override
    public ResponseEntity create(EnvUnitDto dto) {

        EnvUnit item = repo.findByCode(dto.getCode());
        if (item != null) throw new EnvUnitExistsException();

        EnvUnitType itemType = envUnitTypeRepo.getOne(dto.getEnvUnitTypeId());
        if (itemType == null) throw new EnvUnitTypeNotFoundException();

        User user = userRepo.getOne(dto.getUserId());

        item = new EnvUnit();

        MyBeanUtils.copyNonNullProperties(dto, item);
        item.setEnvUnitType(itemType);

        item = repo.save(item);

        Optional<EnvUnitOperName> operName = envUnitOperNameRepo.findFirstByName("Поступление");
        if (!operName.isPresent()) throw new EnvUnitOperNameNotFoundException();

        EnvUnitOper opIncome = new EnvUnitOper();
        opIncome.setEnvUnit(item);
        opIncome.setDateSt(LocalDateTime.now());
        opIncome.setName(operName.get());
        opIncome.setUser(user);
        envUnitOperRepo.save(opIncome);

        return new ResponseEntity(HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity delete(EnvUnitDto dto) {

        EnvUnit item = repo.getOne(dto.getId());
        if (item == null) throw new EnvUnitNotFoundException();

        Optional<EnvUnitOperName> oper = envUnitOperNameRepo.findFirstByName("Поступление");
        if (!oper.isPresent()) throw new EnvUnitOperNameNotFoundException();

        Optional<EnvUnitOper> itemOperNext = envUnitOperRepo.findFirstByEnvUnitIdAndDateFnNotNull(dto.getId());
        if (itemOperNext.isPresent()) {
            throw new EnvUnitOperException();
        }

        Optional<EnvUnitOper> itemOper = envUnitOperRepo.findFirstByEnvUnitIdAndNameEquals(dto.getId(), oper.get());

        EnvUnitOper it = itemOper.orElseThrow(EnvUnitOperNotFoundException::new);

        envUnitOperRepo.delete(it);

        repo.delete(item);

        return new ResponseEntity(HttpStatus.OK);

    }

    @Override
    public ResponseEntity deleteOper(EnvUnitDto dto) {

        EnvUnit item = repo.getOne(dto.getId());
        if (item == null) throw new EnvUnitNotFoundException();

        return null;

    }

    @Override
    public ResponseEntity<List<EnvUnitDto>> getAll(EnvUnitDto filter) {

        List<EnvUnit> items = repo.getFilter(filter);
        List<EnvUnitDto> dtos = items
                .stream()
                .map(item -> mapper.map(item, EnvUnitDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    private EnvUnit findItemIfExists(Long id) {

        Optional<EnvUnit> item = repo.findById(id);

        return item.orElseThrow(EnvUnitNotFoundException::new);

    }

    private EnvUnitOper addEnvUnitOper(EnvUnit envUnit, String name, LocalDateTime date, long userId, LocalDateTime expirationDate) {

        Optional<EnvUnitOperName> operName = envUnitOperNameRepo.findFirstByName(name);
        if (!operName.isPresent()) throw new EnvUnitOperNameNotFoundException();

        EnvUnitOper oper = new EnvUnitOper();
        oper.setName(operName.get());
        oper.setDateSt(date);
        oper.setExpirDate(expirationDate);

        Optional<User> user = userRepo.findById(userId);
        oper.setUser(user.orElseThrow(UserNotFoundException::new));
        oper.setEnvUnit(envUnit);
        return envUnitOperRepo.save(oper);

    }

    private void closeLastOper(EnvUnit envUnit, String name, LocalDateTime date) {

        Optional<EnvUnitOperName> oper = envUnitOperNameRepo.findFirstByName(name);
        if (!oper.isPresent()) throw new EnvUnitOperNameNotFoundException();

        Optional<EnvUnitOper> prevOper = envUnitOperRepo.findFirstByEnvUnitIdAndNameAndDateFnIsNull(envUnit.getId(), oper.get());
        if (prevOper.isPresent()) {
            prevOper.get().setDateFn(date);
            envUnitOperRepo.save(prevOper.get());
        }

    }

    @Override
    public ResponseEntity<List<EnvUnitViewStatDto>> getInstalledInMonthLast12Monthes() {

        List<EnvUnitViewStatDto> res = repo.getInstalledInMonthLast12Monthes();

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<EnvUnitViewStatDto>> getChangedInMonthLast12Monthes() {

        List<EnvUnitViewStatDto> res = repo.getChangedInMonthLast12Monthes();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EnvUnitViewStatDto>> getVerifyInMonthLast12Monthes() {

        List<EnvUnitViewStatDto> res = repo.getVerifyInMonthLast12Monthes();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EnvUnitViewStatDto>> getChangedExpiredInMonthLast12Monthes() {

        List<EnvUnitViewStatDto> res = repo.getChangedExpiredInMonthLast12Monthes();

        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
