package ru.migplus.site.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.migplus.site.dao.main.repo.MenuItemRepository;
import ru.migplus.site.dao.main.repo.PermissionRepository;
import ru.migplus.site.domain.MenuItem;
import ru.migplus.site.domain.Permission;
import ru.migplus.site.dto.menu.MenuItemDto;
import ru.migplus.site.exceptions.MenuItemNotFoundException;
import ru.migplus.site.exceptions.PermissionNotFoundException;
import ru.migplus.site.service.MenuItemService;
import ru.migplus.site.utils.MyBeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository repo;
    private final PermissionRepository permRepo;
    private final ModelMapper mapper;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository repo, PermissionRepository permRepo, ModelMapper mapper) {

        this.repo = repo;
        this.permRepo = permRepo;
        this.mapper = mapper;
        this.mapper.addMappings(new PropertyMap<MenuItemDto, MenuItem>() {
            @Override
            protected void configure() {
                skip(destination.getMenuItem());
            }
        });

    }

    @Override
    public ResponseEntity<List<MenuItemDto>> getItems() {

        return new ResponseEntity<List<MenuItemDto>>(listWithTree(0), HttpStatus.OK);

    }

    @Override
    public List<MenuItemDto> listWithTree(long userId) {

        List<MenuItem> menuItems = repo.getByUserId(userId);
        ArrayList<MenuItemDto> dtos = new ArrayList<>();

        menuItems.forEach(x -> {
            MenuItemDto dto = mapper.map(x, MenuItemDto.class);
            if (x.getMenuItem() != null)
                dto.setParentId(x.getMenuItem().getId());
            dtos.add(dto);
        });
        List<MenuItemDto> result = dtos.stream()
                .filter(meun -> meun.getLevel() == 1)
                .map(menu -> {
                    menu.setTreeMenu(getChildren(menu, dtos));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getOrd() == null ? 0 : menu1.getOrd()) - (menu2.getOrd() == null ? 0 : menu2.getOrd());
                })
                .collect(Collectors.toList());

        return result;

    }

    @Override
    public ResponseEntity<List<MenuItemDto>> getAll() {

        List<MenuItem> items = repo.findAllByOrderByName();
        List<MenuItemDto> dtos = items
                .stream()
                .map(item -> mapper.map(item, MenuItemDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<List<MenuItemDto>>(dtos, HttpStatus.OK);

    }

    @Override
    public ResponseEntity create(MenuItemDto dto) {

        Optional<MenuItem> itemExists = repo.findFirstByNameAndParentId(dto.getName(), (dto.getParentId()) == null ? 0 : dto.getParentId());
        if (itemExists.isPresent())
            return new ResponseEntity(HttpStatus.CONFLICT);
        MenuItem item = new MenuItem();
        MyBeanUtils.copyNonNullProperties(dto, item);

        if (dto.getParentId() != null) {
            MenuItem parentItem = repo.getOne(dto.getParentId());
            if (parentItem == null) throw new MenuItemNotFoundException();
            item.setMenuItem(parentItem);
        }

        Permission permission = permRepo.getOne(dto.getPermissionId());
        if (permission == null) throw new PermissionNotFoundException();

        item.setPermission(permission);
        repo.save(item);

        return new ResponseEntity(HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity delete(MenuItemDto dto) {

        MenuItem item = repo.getOne(dto.getId());
        if (item == null) throw new MenuItemNotFoundException();

        repo.delete(item);

        return new ResponseEntity(HttpStatus.OK);

    }

    public List<MenuItemDto> getChildren(MenuItemDto root, List<MenuItemDto> all) {

        List<MenuItemDto> children = all.stream()
                // According to parent menu ID Find current menu ID，To find the submenu of the current menu
                .filter(menu -> menu.getParentId() == root.getId())
                // Recursively find submenus of submenus
                .map((menu) -> {
                    menu.setTreeMenu(getChildren(menu, all));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getOrd() == null ? 0 : menu1.getOrd()) - (menu2.getOrd() == null ? 0 : menu2.getOrd());
                })
                .collect(Collectors.toList());

        return children;

    }
}
