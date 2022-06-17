package ru.migplus.site.dto.envUnit;

public interface EnvUnitStatusesDto {
    Integer getSuccessCnt();

    Integer getWarningCnt();

    Integer getDangerCnt();

    Boolean getAlert();
}
