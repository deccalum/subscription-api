package se.lexicon.subscriptionapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Locale;
import java.util.Set;

public class CountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {
    private static final Set<String> VALID_CODES = Set.of(Locale.getISOCountries());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null)
            return true;
        return VALID_CODES.contains(value.toUpperCase());
    }
}
