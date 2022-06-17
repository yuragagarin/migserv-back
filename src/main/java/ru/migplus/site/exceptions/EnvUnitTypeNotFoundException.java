package ru.migplus.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Тип единицы не найден")
public class EnvUnitTypeNotFoundException extends RuntimeException {
}
