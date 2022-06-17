package ru.migplus.site.dto.workAct;

import lombok.Data;

@Data
public class WorkActPosDto {
    private Long id;
    private Short typeId;
    private Long unitId;
    private Long operId;
    private long workActId;
    private long userId;
}

