package ru.migplus.site.dto.envUnit;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnvUnitDto {
    @JsonView(Views.IdName.class)
    private Long id;
    @JsonView(Views.IdNameNumber.class)
    private String number;
    @JsonView(Views.IdName.class)
    private String code;
    private long userId;
    private Integer envUnitTypeId;
    private long consumerId;
    private Short workTime; //время службы в месяцах
    private String serialNum;

    private String passport;
    private LocalDateTime installDate;
    private LocalDateTime verifyingDate;
    private long equipId;

    private String envUnitOperName;
    private String envUnitType;
    private String envUnitCategory;
}

