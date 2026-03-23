package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import se.lexicon.subscriptionapi.domain.constant.ActionType;

public record SubscriptionChangeRequest(
        @NotNull ActionType action,

        @Positive(message = "{invalidId}") Long subscriptionId,

        @Positive(message = "{invalidId}") Long planId,

        @Positive(message = "{invalidId}") Long userId
) {}
