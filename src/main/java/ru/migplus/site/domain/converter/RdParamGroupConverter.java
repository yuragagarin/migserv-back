package ru.migplus.site.domain.converter;

import ru.migplus.site.domain.RdJobConfType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class RdParamGroupConverter implements AttributeConverter<RdJobConfType, String> {
    @Override
    public String convertToDatabaseColumn(RdJobConfType rdJobConfType) {
        if (rdJobConfType == null) {
            return null;
        }
        return rdJobConfType.getCode();
    }

    @Override
    public RdJobConfType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(RdJobConfType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
