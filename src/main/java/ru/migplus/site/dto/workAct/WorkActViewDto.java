package ru.migplus.site.dto.workAct;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;

public interface WorkActViewDto {
    @JsonView(Views.IdNum.class)
    Long getId();

    @JsonView(Views.IdNum.class)
    String getNum();

    @JsonView(Views.IdNumDate.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    LocalDateTime getCreateDate();

    @JsonView(Views.Status.class)
    String getStatus();

    @JsonView(Views.Status.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    LocalDateTime getConfirmDate();

    @JsonView(Views.Status.class)
    String getCreateUser();

    @JsonView(Views.Status.class)
    String getConfirmUser();

    @JsonView(Views.Status.class)
    String getConfirmAccountantUser();

    @JsonView(Views.Status.class)
    String getConfirmAccountantDate();
}
