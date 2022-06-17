package ru.migplus.site.dto.role;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class PermissionDto {
    @JsonView(Views.IdName.class)
    int id;
    @JsonView(Views.IdName.class)
    String name;
    @JsonView(Views.Dict.class)
    String code;
    @JsonView(Views.Dict.class)
    String type;
    @JsonView(Views.Dict.class)
    long userId;
}
