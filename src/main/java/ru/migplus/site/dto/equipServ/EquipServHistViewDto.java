package ru.migplus.site.dto.equipServ;

import java.util.Date;

public interface EquipServHistViewDto {
    Long getId();

    String getCode();

    String getName();

    String getEquipNum();

    String getOperName();

    Date getDateSt();

    Date getDateFn();

    Date getNextDate();

    String getStatus();

    Long getEquipServId();
}
