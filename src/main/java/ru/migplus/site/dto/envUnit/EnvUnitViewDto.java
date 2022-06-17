package ru.migplus.site.dto.envUnit;

import java.util.Date;

public interface EnvUnitViewDto {
    long getId();

    String getNumber();

    String getCode();

    String getEnvUnitTypeId();

    String getEnvUnitType();

    String getWorkTime();

    String getPassport();

    String getSerialNum();

    String getEnvUnitCategory();

    String getConsumerName();

    String getEquipName();

    Long getEnvUnitOperId();

    Date getInstallDate();

    Date getVerifyDate();

    Date getNextVerifyDate();

    String getStatus();

    Date getExpirationDate();

    Date getChangeDate();

    Integer getCnt();

    String getMonthNum();
}

