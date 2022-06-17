package ru.migplus.site.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SafeEventType {

    OK("OK"), ERR("ERR");

    @Getter
    private String code;
}
