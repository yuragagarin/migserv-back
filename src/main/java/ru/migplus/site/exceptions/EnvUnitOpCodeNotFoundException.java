package ru.migplus.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Не найдена операция единицы оборудования")
public class EnvUnitOpCodeNotFoundException extends RuntimeException {
}
