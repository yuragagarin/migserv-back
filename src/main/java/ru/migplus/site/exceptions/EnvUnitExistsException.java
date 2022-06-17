package ru.migplus.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Единица оборудования уже есть в наличии")
public class EnvUnitExistsException extends RuntimeException {
}
