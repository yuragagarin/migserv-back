package ru.migplus.site.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class TimestampToZonedDateTimeConverter implements Converter<Timestamp, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(Timestamp timestamp) {
        LocalDateTime withoutTimezone = timestamp.toLocalDateTime();
        ZonedDateTime withTimezone = withoutTimezone.atZone(ZoneId.systemDefault());
        return LocalDateTime.ofInstant(withTimezone.toInstant(), ZoneId.of("Europe/Moscow")).atZone(ZoneId.systemDefault());
        //return LocalDateTime.ofInstant(withTimezone.toInstant(), ZoneId.of("US/Hawaii")).atZone(ZoneId.systemDefault());
    }
}
