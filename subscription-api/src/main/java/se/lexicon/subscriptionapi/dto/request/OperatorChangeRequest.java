package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import se.lexicon.subscriptionapi.domain.constant.ActionType;
import se.lexicon.subscriptionapi.validation.ValidCountryCode;

public record OperatorChangeRequest(
        @NotNull ActionType action,

        @Positive(message = "{invalidId}") Long operatorId,

        @Size(max = 100, message = "{invalidLength}") String name,

        @ValidCountryCode String countryCode) {}
