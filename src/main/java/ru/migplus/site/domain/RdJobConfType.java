package ru.migplus.site.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RdJobConfType {

    DATA("Данные"), SAFE("Безопасность");

    @Getter
    private String code;
}
