package se.lexicon.subscriptionapi.validation;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CountryCodeConverter implements AttributeConverter<CountryCode, String> {
    public String convertToDatabaseColumn(CountryCode c) {
        return c.code();
    }

    public CountryCode convertToEntityAttribute(String s) {
        return new CountryCode(s);
    }
}