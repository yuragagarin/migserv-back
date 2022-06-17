package ru.migplus.site.dto.menu;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuItemDto {
    @JsonView(Views.IdName.class)
    Long id;
    @JsonView(Views.IdName.class)
    String name;
    @JsonView(Views.Dict.class)
    Long parentId;
    @JsonView(Views.Dict.class)
    Integer permissionId;
    @JsonView(Views.Dict.class)
    Short level;
    @JsonView(Views.Dict.class)
    String path;
    @JsonView(Views.Dict.class)
    String iconName;
    @JsonView(Views.Dict.class)
    Integer ord;
    List<MenuItemDto> treeMenu;
}

