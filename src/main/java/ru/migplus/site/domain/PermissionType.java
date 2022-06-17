package ru.migplus.site.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PermissionType {

    MENU("Меню"), VIEW("Видимость"), ACITON("Действия");

    @Getter
    private String code;
}
