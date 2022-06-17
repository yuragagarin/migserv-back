package ru.migplus.site.dto.workAct;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkActDto {
    @JsonView(Views.IdNum.class)
    private Long id;
    @JsonView(Views.IdNum.class)
    private String num;
    private long userId;
    private List<WorkActPosDto> poss;
    @JsonView(Views.IdNumDate.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDateTime date;
    public String roleName;
}

