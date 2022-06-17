package ru.migplus.site.domain.converter;

import ru.migplus.site.domain.RdJobConfState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class RdJobConfStateConverter implements AttributeConverter<RdJobConfState, String> {
    @Override
    public String convertToDatabaseColumn(RdJobConfState rdJobConfState) {
        if (rdJobConfState == null) {
            return null;
        }
        return rdJobConfState.getCode();
    }

    @Override
    public RdJobConfState convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(RdJobConfState.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
