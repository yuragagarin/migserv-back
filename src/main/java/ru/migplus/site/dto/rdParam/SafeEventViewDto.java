package ru.migplus.site.dto.rdParam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.ZonedDateTime;

public interface SafeEventViewDto {
    long getId();

    String getName();

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    ZonedDateTime getStDate();

    String getUserName();

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    ZonedDateTime getFnDate();

    String getComment();

    @JsonView(Views.ForRepairCnt.class)
    Integer getForRepairCnt();

    String getDuration();
}

