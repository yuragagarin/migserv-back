package ru.migplus.site.dto.rdParam;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;


public interface SafeEventTimelineDto {
    String getParamName();

    String getEventName();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    ZonedDateTime getStDate();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    ZonedDateTime getFnDate();
}

