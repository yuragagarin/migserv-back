package ru.migplus.site.dto.consumer;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class ConsumerDto {
    @JsonView(Views.IdName.class)
    private long id;
    @JsonView(Views.IdName.class)
    private String name;
    @JsonView(Views.Dict.class)
    private String address;
    @JsonView(Views.Dict.class)
    private String designated;
    @JsonView(Views.Dict.class)
    private String designatedPhone;
    @JsonView(Views.Dict.class)
    private long servicemanId;
    @JsonView(Views.Dict.class)
    private String serviceman;
    @JsonView(Views.Dict.class)
    private long servicemanGasId;
    @JsonView(Views.Dict.class)
    private String servicemanGas;
    private String status;
    private long servicemanOrGasId;
    Long userId;
}

