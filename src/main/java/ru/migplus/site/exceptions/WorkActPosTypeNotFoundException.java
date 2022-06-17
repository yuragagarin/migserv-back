package ru.migplus.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Тип акта не найден")
public class WorkActPosTypeNotFoundException extends RuntimeException {
}
