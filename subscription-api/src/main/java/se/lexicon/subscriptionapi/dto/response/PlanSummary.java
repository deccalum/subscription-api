package se.lexicon.subscriptionapi.dto.response;

import java.math.BigDecimal;
import se.lexicon.subscriptionapi.domain.constant.PlanKind;
import se.lexicon.subscriptionapi.domain.constant.PlanStatus;

public record PlanSummary(
        Long id,
        String name,
        BigDecimal price,
        PlanKind kind,
        PlanStatus status) {}
