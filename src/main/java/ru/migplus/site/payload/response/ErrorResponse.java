package ru.migplus.site.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.migplus.site.exceptions.Error;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private Boolean success = false;

    private Error error;

    @Builder
    public ErrorResponse(Date timestamp, int status, String code, String message, String details) {
        this.error = new Error(timestamp, status, code, message, details);
    }
}
