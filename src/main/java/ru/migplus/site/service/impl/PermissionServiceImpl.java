package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.migplus.site.dao.main.filter.PermissionSpec;
import ru.migplus.site.dao.main.repo.PermissionRepository;
import ru.migplus.site.domain.Permission;
import ru.migplus.site.domain.converter.PermissionTypeConverter;
import ru.migplus.site.dto.role.PermissionDto;
import ru.migplus.site.exceptions.PermissionNotFoundException;
import ru.migplus.site.service.PermissionService;
import ru.migplus.site.utils.MyBeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository repo;
    private final ModelMapper mapper;

    @Autowired
    public PermissionServiceImpl(PermissionRepository repo, ModelMapper mapper) {

        this.repo = repo;
        this.mapper = mapper;

    }

    @Override
    public ResponseEntity<List<PermissionDto>> getAll(PermissionSpec filter) {

        List<Permission> items = repo.findAll(filter, Sort.by(Sort.Direction.ASC, "name"));
        List<PermissionDto> dtos = items
                .stream()
                .map(item -> mapper.map(item, PermissionDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    public ResponseEntity create(PermissionDto dto) {

        Permission item = new Permission();
        PermissionTypeConverter converter = new PermissionTypeConverter();

        MyBeanUtils.copyNonNullProperties(dto, item);

        item.setType(converter.convertToEntityAttribute(dto.getType()));

        repo.save(item);

        return new ResponseEntity(HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity delete(PermissionDto dto) {

        Permission item = repo.getOne(dto.getId());
        if (item == null) throw new PermissionNotFoundException();

        repo.delete(item);

        return new ResponseEntity(HttpStatus.OK);

    }
}
