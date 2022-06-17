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
public class ParamPerHourDto {
    @JsonView(Views.NameValueMinute.class)
    String name;
    @JsonView(Views.NameValueMinute.class)
    BigDecimal val;
    long consumerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date time;
    String hour;
    @JsonView(Views.NameValueMinute.class)
    String minute;
    List<Long> paramIds;

    @Builder
    public ParamPerHourDto(Date time, String hour) {
        this.hour = hour;
        this.time = time;
    }

    @Builder
    public ParamPerHourDto(String name, BigDecimal val, String minute) {
        this.name = name;
        this.val = val;
        this.minute = minute;
    }
}

