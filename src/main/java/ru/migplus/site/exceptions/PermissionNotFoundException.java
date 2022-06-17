package ru.migplus.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Разрешение не найдено")
public class PermissionNotFoundException extends RuntimeException {
}
