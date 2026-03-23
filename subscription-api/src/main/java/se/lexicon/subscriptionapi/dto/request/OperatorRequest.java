package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import se.lexicon.subscriptionapi.validation.ValidCountryCode;


public record OperatorRequest(
        @NotBlank(message = "{blank}") @ValidCountryCode String countryCode,

        @NotBlank(message = "{blank}") @Size(max = 100, message = "{invalidLength}") String name,

        Instant writeInstant
) {}
