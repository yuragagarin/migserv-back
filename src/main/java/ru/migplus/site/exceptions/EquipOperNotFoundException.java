package ru.migplus.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Операция с оборудованием не найдена")
public class EquipOperNotFoundException extends RuntimeException {
}
