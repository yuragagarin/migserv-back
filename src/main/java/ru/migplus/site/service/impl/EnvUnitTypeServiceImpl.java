package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.migplus.site.dao.main.repo.EnvUnitTypeRepository;
import ru.migplus.site.domain.EnvUnitType;
import ru.migplus.site.dto.EnvUnitTypeDto;
import ru.migplus.site.service.EnvUnitTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnvUnitTypeServiceImpl implements EnvUnitTypeService {

    private final EnvUnitTypeRepository repo;
    private final ModelMapper mapper;


    @Autowired
    public EnvUnitTypeServiceImpl(EnvUnitTypeRepository repo, ModelMapper mapper) {

        this.repo = repo;
        this.mapper = mapper;

    }

    @Override
    public ResponseEntity<List<EnvUnitTypeDto>> getAll() {

        List<EnvUnitType> items = repo.findAll();
        List<EnvUnitTypeDto> dtos = items
                .stream()
                .map(item -> mapper.map(item, EnvUnitTypeDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

}
