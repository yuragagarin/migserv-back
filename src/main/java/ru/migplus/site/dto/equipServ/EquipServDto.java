package ru.migplus.site.dto.equipServ;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EquipServDto {
    private long consumerId;
    private String code;
    private String name;
    private String number;
    private String operName;
    private LocalDateTime nextDate;
    private long userId;
}

