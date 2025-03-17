package com.fortune.code.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return Boolean.TRUE.equals(attribute) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if((!"Y".equals(dbData) && !"N".equals(dbData)))
            throw new IllegalArgumentException("dbData must be Y or N");

        return "Y".equals(dbData);
    }
}
