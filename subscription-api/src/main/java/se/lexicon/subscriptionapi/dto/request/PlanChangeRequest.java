package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.Range;
import se.lexicon.subscriptionapi.domain.constant.ActionType;
import se.lexicon.subscriptionapi.domain.constant.NetworkGeneration;
import se.lexicon.subscriptionapi.domain.constant.PlanKind;
import se.lexicon.subscriptionapi.domain.constant.PlanStatus;
import se.lexicon.subscriptionapi.validation.CountryCode;

public record PlanChangeRequest(
        @NotNull ActionType action,

        @Positive(message = "{invalidId}") Long planId,

        PlanKind kind,

        @Size(max = 200, message = "{invalidLength}") String name,
        @Positive(message = "{positive}") @Digits(integer = 10, fraction = 2, message = "{digits}") BigDecimal price,
        PlanStatus status,
        @Positive(message = "{positive}") Integer uploadSpeedMbps,
        @Positive(message = "{positive}") Integer downloadSpeedMbps,
        NetworkGeneration networkGeneration,
        @Positive(message = "{positive}") Integer dataLimitGb,
        @Positive(message = "{positive}") @Digits(integer = 8, fraction = 4, message = "{digits}") BigDecimal callCostPerMinute,
        @Positive(message = "{positive}") @Digits(integer = 8, fraction = 4, message = "{digits}") BigDecimal smsCostPerMessage,
        List<CountryCode> coverage,
        @Range(min = 100, max = 10000, message = "{range}") Integer MHz
) {
    @AssertTrue(message = "{invalidPlanPayload}")
    public boolean isPlanPayloadValidForKind() {
        if (action == null || action == ActionType.DELETE)
            return true;
        if (kind == null)
            return false;
        return switch (kind) {
            case INTERNET ->
                uploadSpeedMbps != null&& downloadSpeedMbps != null&& networkGeneration == null&& dataLimitGb == null&& callCostPerMinute == null&& smsCostPerMessage == null;
            case CELLULAR ->
                uploadSpeedMbps == null&& downloadSpeedMbps == null&& networkGeneration != null&& dataLimitGb != null&& callCostPerMinute != null&& smsCostPerMessage != null;
            case SATELLITE ->
                uploadSpeedMbps == null&& downloadSpeedMbps == null&& networkGeneration == null&& dataLimitGb == null&& callCostPerMinute == null&& smsCostPerMessage == null;
        };
    }
}
