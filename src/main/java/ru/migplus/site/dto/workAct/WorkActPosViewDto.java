package ru.migplus.site.dto.workAct;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface WorkActPosViewDto {
    Long getId();

    String getType();

    String getDescr();

    @JsonFormat(pattern = "dd.MM.yyyy")
    LocalDateTime getDate();
}
