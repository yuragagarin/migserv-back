package ru.migplus.site.dto.equipServ;

import java.util.Date;

public interface EquipServViewDto {
    long getEquipOperId();

    String getName();

    String getNumber();

    String getCode();

    String getOperName();

    Date getDate();

    Date getNextDate();

    String getStatus();
}
