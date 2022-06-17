package ru.migplus.site.domain.converter;

import ru.migplus.site.domain.PermissionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class PermissionTypeConverter implements AttributeConverter<PermissionType, String> {
    @Override
    public String convertToDatabaseColumn(PermissionType type) {
        if (type == null) {
            return null;
        }
        return type.getCode();
    }

    @Override
    public PermissionType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(PermissionType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
