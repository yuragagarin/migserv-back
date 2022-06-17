package ru.migplus.site.dto.consumer;

import com.fasterxml.jackson.annotation.JsonView;

public interface ConsumerViewDto {
    @JsonView(Views.IdName.class)
    long getId();

    @JsonView(Views.IdName.class)
    String getName();
}

