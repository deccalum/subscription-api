package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.Range;
import se.lexicon.subscriptionapi.domain.constant.NetworkGeneration;
import se.lexicon.subscriptionapi.domain.constant.PlanKind;
import se.lexicon.subscriptionapi.domain.constant.PlanStatus;
import se.lexicon.subscriptionapi.validation.CountryCode;

public record PlanRequest(
        @NotNull(message = "{required}") PlanKind kind,

        @NotBlank(message = "{blank}") String name,

        @NotNull(message = "{required}") Long operator,

        @NotNull(message = "{required}") @Positive(message = "{positive}") @Digits(integer = 10, fraction = 2, message = "{digits}") BigDecimal price,

        @NotNull(message = "{required}") PlanStatus status,

        @Positive(message = "{positive}") @Digits(integer = 5, fraction = 0, message = "{digits}") Integer uploadSpeedMbps,

        @Positive(message = "{positive}") @Digits(integer = 5, fraction = 0, message = "{digits}") Integer downloadSpeedMbps,

        @Positive(message = "{positive}") @Digits(integer = 5, fraction = 2, message = "{digits}") Integer dataLimitGb,

        @Positive(message = "{positive}") @Digits(integer = 8, fraction = 4, message = "{digits}") BigDecimal callCostPerMinute,

        @Positive(message = "{positive}") @Digits(integer = 8, fraction = 4, message = "{digits}") BigDecimal smsCostPerMessage,

        NetworkGeneration networkGeneration,

        List<CountryCode> coverage,

        @Range(min = 100, max = 10000, message = "{range}") Integer MHz
) {
    @AssertTrue(message = "{invalidPlanPayload}")
    public boolean isPlanPayloadValidForKind() {
        if (kind == null)
            return true;
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
