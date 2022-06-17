package ru.migplus.site.dto.equip;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface EquipViewDto {
    long getId();

    String getConsumerName();

    String getName();

    String getNumber();

    String getYear();

    String getOperName();
}
