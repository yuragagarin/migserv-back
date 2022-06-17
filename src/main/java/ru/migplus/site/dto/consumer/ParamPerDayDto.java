package ru.migplus.site.dto.consumer;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamPerDayDto {
    @JsonView(Views.NameValueHour.class)
    String name;
    @JsonView(Views.NameValueHour.class)
    BigDecimal val;
    long consumerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date time;
    @JsonView(Views.NameValueHour.class)
    String hour;
    List<Long> paramIds;

    @Builder
    public ParamPerDayDto(String name, BigDecimal val, String hour) {
        this.name = name;
        this.val = val;
        this.hour = hour;
    }
}

