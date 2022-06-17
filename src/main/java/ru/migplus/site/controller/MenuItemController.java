package ru.migplus.site.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.migplus.site.dto.menu.MenuItemDto;
import ru.migplus.site.dto.menu.Views;
import ru.migplus.site.service.MenuItemService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class MenuItemController extends BaseController {

    private final MenuItemService serv;

    @Autowired
    public MenuItemController(MenuItemService service) {
        this.serv = service;
    }

    @GetMapping("/menu-items")
    ResponseEntity<List<MenuItemDto>> getAll() {
        return serv.getItems();
    }

    @GetMapping("/menu-items/idn")
    @JsonView(Views.IdName.class)
    ResponseEntity<List<MenuItemDto>> getIdName() {
        return serv.getAll();
    }

    @GetMapping("/menu-items/dict")
    @JsonView(Views.Dict.class)
    ResponseEntity<List<MenuItemDto>> getDict() {
        return serv.getAll();
    }

    @PostMapping(value = "/menu-item")
    public ResponseEntity create(@RequestBody MenuItemDto dto) {
        return serv.create(dto);
    }

    @DeleteMapping("/menu-item/{id}")
    public void delete(@PathVariable long id) {

        MenuItemDto dto = new MenuItemDto();
        dto.setId(id);
        serv.delete(dto);

    }
}
