package ru.migplus.site.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.migplus.site.payload.response.ErrorResponse;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {
    //@ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleThrowable(final Throwable ex, WebRequest request) {
        String message = "Не обработанное исключение на сервере";
        log.error(message, ex);
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(new Date(), 500, "INTERNAL_SERVER_ERROR", message, request.getDescription(false)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        String message = "Пользователь или пароль не найден";
        log.error(message, ex);
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(new Date(), 404, "USER_OR_PASS_NOT_FOUND", message, request.getDescription(false)),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(final UserNotFoundException ex, WebRequest request) {
        String message = "Пользователь не найден";
        log.error(message, ex);
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(new Date(), 404, "USER_NOT_FOUND", message, request.getDescription(false)),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EnvUnitNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(final EnvUnitNotFoundException ex, WebRequest request) {
        String message = "Единица оборудования не найдена";
        log.error(message, ex);
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(new Date(), 404, "ENV_UNIT_NOT_FOUND", message, request.getDescription(false)),
                HttpStatus.NOT_FOUND);
    }
}
