package ru.migplus.site.service;

import org.springframework.http.ResponseEntity;
import ru.migplus.site.dto.menu.MenuItemDto;

import java.util.List;

public interface MenuItemService {
    ResponseEntity<List<MenuItemDto>> getItems();

    List<MenuItemDto> listWithTree(long userId);

    ResponseEntity<List<MenuItemDto>> getAll();

    ResponseEntity create(MenuItemDto dto);

    ResponseEntity delete(MenuItemDto dto);
}
