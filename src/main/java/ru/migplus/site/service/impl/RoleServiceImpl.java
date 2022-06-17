package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.migplus.site.dao.main.filter.RoleSpec;
import ru.migplus.site.dao.main.repo.PermissionRepository;
import ru.migplus.site.dao.main.repo.RoleRepository;
import ru.migplus.site.domain.Permission;
import ru.migplus.site.domain.Role;
import ru.migplus.site.dto.role.RoleDto;
import ru.migplus.site.exceptions.RoleNotFoundException;
import ru.migplus.site.service.RoleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repo;
    private final PermissionRepository permissionRepo;
    private final ModelMapper mapper;

    @PersistenceContext
    EntityManager em;

    @Autowired
    public RoleServiceImpl(RoleRepository repo, PermissionRepository permissionRepo, ModelMapper mapper) {

        this.repo = repo;
        this.permissionRepo = permissionRepo;
        this.mapper = mapper;

    }

    @Override
    public ResponseEntity<List<RoleDto>> getAll(RoleSpec filter) {

        List<Role> items = repo.findAll(filter, Sort.by(Sort.Direction.ASC, "name"));
        List<RoleDto> dtos = items
                .stream()
                .map(item -> mapper.map(item, RoleDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity changePermissions(RoleDto dto) {

        Role item = repo.getOne(dto.getId());
        if (item == null) throw new RoleNotFoundException();

        permissionRepo.deletePermissionsByRoleId(dto.getId());

        item = repo.getOne(dto.getId());
        List<Permission> permissions = permissionRepo.findByIdIn(dto.getPermissionsId());
        item.setPermissions(permissions);

        repo.save(item);

        return new ResponseEntity(HttpStatus.OK);

    }
}
