package ru.migplus.site.dto.rdParam;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class SafeParamStateRepairDto {
    private long safeEventId;
    private ZonedDateTime repairDate;
    private String comment;
    private long userId;
}

