package se.lexicon.subscriptionapi.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import se.lexicon.subscriptionapi.domain.constant.NetworkGeneration;
import se.lexicon.subscriptionapi.domain.constant.PlanKind;
import se.lexicon.subscriptionapi.domain.constant.PlanStatus;
import se.lexicon.subscriptionapi.domain.constant.RequestActionType;
import se.lexicon.subscriptionapi.domain.constant.RequestStatus;

public record ChangeRequestResponse(
        Long id,
        RequestActionType actionType,
        RequestStatus status,
        Instant request_instant,
        Instant review_instant,
        String rejectionReason,

        Long requestedById,
        String requestedByEmail,
        Long reviewedById,
        String reviewedByEmail,

        // Plan payload (CREATE_PLAN, UPDATE_PLAN)
        Long targetPlanId,
        PlanKind planKind,
        String planName,
        BigDecimal planPrice,
        PlanStatus planStatus,
        Integer uploadSpeedMbps,
        Integer downloadSpeedMbps,
        NetworkGeneration networkGeneration,
        Integer dataLimitGb,
        BigDecimal callCostPerMinute,
        BigDecimal smsCostPerMessage,

        // Operator payload (UPDATE_OPERATOR)
        Long targetOperatorId,
        String newOperatorName,

        // Subscription payload (CREATE_SUBSCRIPTION)
        Long targetUserId
) {}
