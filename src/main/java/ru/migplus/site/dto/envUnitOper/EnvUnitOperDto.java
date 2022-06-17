package ru.migplus.site.dto.envUnitOper;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.migplus.site.dto.envUnit.Views;

import java.time.LocalDateTime;

@Data
public class EnvUnitOperDto {
    @JsonView(Views.IdName.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String name;
    private Long envUnitId;
}

