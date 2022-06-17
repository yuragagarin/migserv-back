package ru.migplus.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Акт ещё не подтверждён бухгалтером")
public class WorkActOperNoConfirmedException extends RuntimeException {
}
