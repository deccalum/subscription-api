package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.*;

public record CreateSubscriptionChangeRequest(
        @NotNull(message = "{required}")
        @Positive(message = "{invalidId}")
        Long targetPlanId,

        @NotNull(message = "{required}")
        @Positive(message = "{invalidId}")
        Long targetUserId
) {}
