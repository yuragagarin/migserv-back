package ru.migplus.site.domain;

import lombok.*;

@AllArgsConstructor
public enum RdJobConfState {

    STARTED("Запущен"), STOPPED("Остановлен");

    @Getter
    private String code;
}
