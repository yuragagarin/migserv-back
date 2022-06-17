package ru.migplus.site.domain.converter;

import ru.migplus.site.domain.SafeEventType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class SafeEventTypeConverter implements AttributeConverter<SafeEventType, String> {
    @Override
    public String convertToDatabaseColumn(SafeEventType safeEventType) {
        if (safeEventType == null) {
            return null;
        }
        return safeEventType.getCode();
    }

    @Override
    public SafeEventType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(SafeEventType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
