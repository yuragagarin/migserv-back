package ru.migplus.site.domain;

import lombok.*;

@AllArgsConstructor
public enum EnvUnitOperName_old {

    INCOME("Поступление"), SETUP("Установка"), VERIFYING("Поверка"), CHANGING("Замена"), CONFIRM("Подтверждение"), DISCARD("Списание");

    @Getter
    private String code;
}
