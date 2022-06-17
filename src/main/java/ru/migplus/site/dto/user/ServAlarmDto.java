package ru.migplus.site.dto.user;

public interface ServAlarmDto {
    Integer getEnvSuccessCnt();

    Integer getEnvChangeWarningCnt();

    Integer getEnvVerifyWarningCnt();

    Integer getEnvChangeDangerCnt();

    Integer getEnvVerifyDangerCnt();

    Integer getEquipSuccessCnt();

    Integer getEquipWarningCnt();

    Integer getEquipDangerCnt();

    Integer getSafeParamDangerCnt();

    Integer getSafeParamAllCnt();
}
