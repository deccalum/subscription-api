package se.lexicon.subscriptionapi.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import se.lexicon.subscriptionapi.domain.constant.ActionType;
import se.lexicon.subscriptionapi.domain.constant.NetworkGeneration;
import se.lexicon.subscriptionapi.domain.constant.PlanKind;
import se.lexicon.subscriptionapi.domain.constant.PlanStatus;
import se.lexicon.subscriptionapi.domain.constant.RequestStatus;
import se.lexicon.subscriptionapi.domain.entity.User;
import se.lexicon.subscriptionapi.domain.entity.request.CreatePlanRequest;
import se.lexicon.subscriptionapi.domain.entity.request.CreateSubscriptionRequest;
import se.lexicon.subscriptionapi.domain.entity.request.DeletePlanRequest;
import se.lexicon.subscriptionapi.domain.entity.request.UpdateOperatorRequest;
import se.lexicon.subscriptionapi.domain.entity.request.UpdatePlanRequest;


public record ChangeRequestResponse(
        Long id,
        ActionType actionType,
        RequestStatus status,
        Instant request_instant,
        Instant review_instant,
        String rejectionReason,

        Long requestedById,
        String requestedByEmail,
        Long reviewedById,
        String reviewedByEmail,

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

        Long targetOperatorId,
        String newOperatorName,

        Long targetUserId
) {
    public static ChangeRequestResponse forCreatePlan(CreatePlanRequest e) {
        return new ChangeRequestResponse(
                e.getId(),
                e.getActionType(),
                e.getStatus(),
                e.getRequest_instant(),
                e.getReview_instant(),
                e.getRejectionReason(),
                idOf(e.getRequestedBy()),
                emailOf(e.getRequestedBy()),
                idOf(e.getReviewedBy()),
                emailOf(e.getReviewedBy()),
                null,
                e.getPlanKind(),
                e.getPlanName(),
                e.getPlanPrice(),
                e.getPlanStatus(),
                e.getUploadSpeedMbps(),
                e.getDownloadSpeedMbps(),
                e.getNetworkGeneration(),
                e.getDataLimitGb(),
                e.getCallCostPerMinute(),
                e.getSmsCostPerMessage(),
                null,
                null,
                null
        );
    }

    public static ChangeRequestResponse forUpdatePlan(UpdatePlanRequest e) {
        return new ChangeRequestResponse(
                e.getId(),
                e.getActionType(),
                e.getStatus(),
                e.getRequest_instant(),
                e.getReview_instant(),
                e.getRejectionReason(),
                idOf(e.getRequestedBy()),
                emailOf(e.getRequestedBy()),
                idOf(e.getReviewedBy()),
                emailOf(e.getReviewedBy()),
                e.getTargetPlanId(),
                e.getPlanKind(),
                e.getPlanName(),
                e.getPlanPrice(),
                e.getPlanStatus(),
                e.getUploadSpeedMbps(),
                e.getDownloadSpeedMbps(),
                e.getNetworkGeneration(),
                e.getDataLimitGb(),
                e.getCallCostPerMinute(),
                e.getSmsCostPerMessage(),
                null,
                null,
                null
        );
    }

    public static ChangeRequestResponse forDeletePlan(DeletePlanRequest e) {
        return new ChangeRequestResponse(
                e.getId(),
                e.getActionType(),
                e.getStatus(),
                e.getRequest_instant(),
                e.getReview_instant(),
                e.getRejectionReason(),
                idOf(e.getRequestedBy()),
                emailOf(e.getRequestedBy()),
                idOf(e.getReviewedBy()),
                emailOf(e.getReviewedBy()),
                e.getTargetPlanId(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChangeRequestResponse forUpdateOperator(UpdateOperatorRequest e) {
        return new ChangeRequestResponse(
                e.getId(),
                e.getActionType(),
                e.getStatus(),
                e.getRequest_instant(),
                e.getReview_instant(),
                e.getRejectionReason(),
                idOf(e.getRequestedBy()),
                emailOf(e.getRequestedBy()),
                idOf(e.getReviewedBy()),
                emailOf(e.getReviewedBy()),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                e.getTargetOperatorId(),
                e.getNewOperatorName(),
                null
        );
    }

    public static ChangeRequestResponse forCreateSubscription(CreateSubscriptionRequest e) {
        return new ChangeRequestResponse(
                e.getId(),
                e.getActionType(),
                e.getStatus(),
                e.getRequest_instant(),
                e.getReview_instant(),
                e.getRejectionReason(),
                idOf(e.getRequestedBy()),
                emailOf(e.getRequestedBy()),
                idOf(e.getReviewedBy()),
                emailOf(e.getReviewedBy()),
                e.getTargetPlanId(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                e.getTargetUserId()
        );
    }

    private static Long idOf(User u) {
        return u == null ? null : u.getId();
    }
    private static String emailOf(User u) {
        return u == null ? null : u.getEmail();
    }
}
