package ru.migplus.site.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public interface UserViewDto {
    long getId();

    String getUsername();

    String getFio();

    String getPhoneNumber();

    String getEmail();

    String getPost();

    String getConsumer();

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    ZonedDateTime getUpdated();

    String getMasterFio();

    String getRoleName();
}
