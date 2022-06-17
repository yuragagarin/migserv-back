package ru.migplus.site.dto.equipServ;

public interface EquipServStatusesDto {
    Integer getSuccessCnt();

    Integer getWarningCnt();

    Integer getDangerCnt();

    Boolean getAlert();
}
