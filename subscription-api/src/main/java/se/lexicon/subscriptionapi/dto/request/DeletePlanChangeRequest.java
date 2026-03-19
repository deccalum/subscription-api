package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.*;

public record DeletePlanChangeRequest(
        @NotNull(message = "{required}")
        @Positive(message = "{invalidId}")
        Long targetPlanId
) {}
