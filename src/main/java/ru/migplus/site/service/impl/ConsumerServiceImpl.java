package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.migplus.site.dao.main.filter.ConsumerSpecs;
import ru.migplus.site.dao.main.repo.ConsumerRepository;
import ru.migplus.site.dao.main.repo.RdParamRepository;
import ru.migplus.site.dao.main.repo.UserRepository;
import ru.migplus.site.domain.Consumer;
import ru.migplus.site.domain.Status;
import ru.migplus.site.domain.User;
import ru.migplus.site.dto.consumer.ConsumerDto;
import ru.migplus.site.dto.consumer.ConsumerViewDto;
import ru.migplus.site.exceptions.ConsumerNotFoundException;
import ru.migplus.site.security.services.UserDetailsImpl;
import ru.migplus.site.service.ConsumerService;
import ru.migplus.site.utils.MyBeanUtils;
import ru.migplus.site.utils.StrUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository repo;
    private final UserRepository userRepo;
    private final ModelMapper mapper;

    @Autowired
    public ConsumerServiceImpl(ConsumerRepository repo, UserRepository userRepo, RdParamRepository rdParamRepo, ModelMapper mapper) {

        this.repo = repo;
        this.userRepo = userRepo;
        this.mapper = mapper;

        Converter<User, String> initials = //ФИО в формате Иванов И.И.
                ctx -> ctx.getSource() == null ? null : StrUtils.generateFullName(ctx.getSource().getSurname()
                        , ctx.getSource().getName(), ctx.getSource().getPatronymic());

        /*this.mapper.createTypeMap(Consumer.class, ConsumerDto.class)
                .addMappings(
                        mp -> mp.using(initials).<String>map(src -> src.getServiceman(), (dest, v) -> dest.setServiceman(v))
                );*/

        this.mapper.createTypeMap(Consumer.class, ConsumerDto.class)
                .addMappings(
                        new PropertyMap<Consumer, ConsumerDto>() {
                            @Override
                            protected void configure() {
                                // define a converter that takes the whole "person"
                                //using(initials).<String>map(source.getServiceman()).setServiceman(null);
                                using(initials).<String>map(source.getServiceman()).setServiceman(null);
                                using(initials).<String>map(source.getServicemanGas()).setServicemanGas(null);
                            }
                        });

    }

    @Override
    public ResponseEntity<List<ConsumerDto>> getAll(ConsumerDto filt, UserDetailsImpl userInfo) {

        if (!userInfo.getRoles().contains("Администратор")) filt.setStatus(Status.ACTIVE.toString());
        if (userInfo.getRoles().contains("Клиент")) filt.setId(userInfo.getConsumerId());
        if (userInfo.getRoles().contains("Инженер-наладчик газ. обор-я") ||
                userInfo.getRoles().contains("Инженер-наладчик кот. обор-я")) {
            filt.setServicemanOrGasId(userInfo.getId());
        }

        Status status = (filt.getStatus() != null) ? Status.valueOf(filt.getStatus()) : null;
        Specification<Consumer> specs = Specification
                .where(ConsumerSpecs.equalId(filt.getId())
                        .and(ConsumerSpecs.equalStatus(status))
                        .and(ConsumerSpecs.equalServicemanOrServicemanGas(filt.getServicemanOrGasId())));

        List<Consumer> items = repo.findAll(specs);

        List<ConsumerDto> dtos = items
                .stream()
                .map(item -> mapper.map(item, ConsumerDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<ConsumerViewDto>> getAllWithParams(ConsumerDto filt) {

        List<ConsumerViewDto> dtos = repo.findAllWithParams();

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity create(ConsumerDto dto) {

        Consumer item = new Consumer();
        MyBeanUtils.copyNonNullProperties(dto, item);

        User user = userRepo.getOne(dto.getUserId());
        item.setUser(user);
        Consumer consumer = repo.findFirstByOrderByIdDesc().get();
        if (consumer == null)
            item.setRdDstName("rd_consumer_1");
        else {
            String base = consumer.getRdDstName().replaceAll("[0-9]", "");
            Long num = Long.valueOf(consumer.getRdDstName().replaceAll("[^0-9]", ""));
            item.setRdDstName(base + (num + 1));
        }

        Optional<User> serviceman = userRepo.findById(dto.getServicemanId());
        if (serviceman.isPresent()) {
            item.setServiceman(serviceman.get());
        }

        Optional<User> servicemanGas = userRepo.findById(dto.getServicemanGasId());
        if (servicemanGas.isPresent()) {
            item.setServicemanGas(servicemanGas.get());
        }

        repo.save(item);

        return new ResponseEntity(HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<ConsumerDto> get(long id) {

        Consumer item = repo.findFirstByIdAndStatus(id, Status.ACTIVE).get();
        if (item == null) throw new ConsumerNotFoundException();
        ConsumerDto dto = new ConsumerDto();
        MyBeanUtils.copyNonNullProperties(item, dto);

        dto.setServicemanId((item.getServiceman() == null) ? 0 : item.getServiceman().getId());
        dto.setServicemanGasId((item.getServicemanGas() == null) ? 0 : item.getServicemanGas().getId());

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @Override
    public ResponseEntity update(ConsumerDto dto) {

        Consumer item = repo.findFirstByIdAndStatus(dto.getId(), Status.ACTIVE).get();
        if (item == null)
            throw new ConsumerNotFoundException();
        MyBeanUtils.copyNonNullProperties(dto, item);

        Optional<User> serviceman = userRepo.findById(dto.getServicemanId());
        if (serviceman.isPresent()) {
            item.setServiceman(serviceman.get());
        }

        Optional<User> servicemanGas = userRepo.findById(dto.getServicemanGasId());
        if (servicemanGas.isPresent()) {
            item.setServicemanGas(servicemanGas.get());
        }

        item = repo.saveAndFlush(item);

        return new ResponseEntity(item, HttpStatus.OK);

    }

    @Override
    public ResponseEntity delete(ConsumerDto dto) {

        Consumer item = repo.getOne(dto.getId());
        if (item == null) throw new ConsumerNotFoundException();
        item.setStatus(Status.DELETED);
        repo.save(item);

        return new ResponseEntity(HttpStatus.OK);

    }


}
