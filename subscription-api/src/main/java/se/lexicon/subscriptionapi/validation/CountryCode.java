package se.lexicon.subscriptionapi.validation;

import java.util.Locale;
import java.util.Set;

public record CountryCode(String code) {
    private static final Set<String> VALID_CODES = Set.of(Locale.getISOCountries());

    public CountryCode {
        if (code == null || !VALID_CODES.contains(code.toUpperCase()))
            throw new IllegalArgumentException("Invalid country code: " + code);
        code = code.toUpperCase();
    }
}
