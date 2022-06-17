package ru.migplus.site.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Error {
    private Date timestamp;
    private int status;
    private String code;
    private String message;
    private String details;
}
