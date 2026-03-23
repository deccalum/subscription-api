package se.lexicon.subscriptionapi.mapper;

import java.util.Optional;
import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.domain.constant.RequestStatus;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;
import se.lexicon.subscriptionapi.domain.entity.ChangeRequest;
import se.lexicon.subscriptionapi.domain.entity.request.CreatePlanRequest;
import se.lexicon.subscriptionapi.domain.entity.request.CreateSubscriptionRequest;
import se.lexicon.subscriptionapi.domain.entity.request.DeletePlanRequest;
import se.lexicon.subscriptionapi.domain.entity.request.UpdateOperatorRequest;
import se.lexicon.subscriptionapi.domain.entity.request.UpdatePlanRequest;
import se.lexicon.subscriptionapi.domain.entity.user.UserAdmin;
import se.lexicon.subscriptionapi.domain.entity.user.UserOperator;
import se.lexicon.subscriptionapi.dto.request.OperatorChangeRequest;
import se.lexicon.subscriptionapi.dto.request.OperatorRequest;
import se.lexicon.subscriptionapi.dto.request.PlanChangeRequest;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.request.SubscriptionChangeRequest;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.ChangeRequestResponse;

@Component
public class RequestMapper {
    private static void baseValues(ChangeRequest entity, UserOperator operator) {
        entity.setStatus(RequestStatus.PENDING.create());
        entity.setRequestedBy(operator);
    }

    // --- PlanChangeRequest → entity ---

    public ChangeRequest toEntity(PlanChangeRequest request, UserOperator operator) {
        return Optional.ofNullable(request)
                .map(r -> {
                    ChangeRequest entity = r.action().create();
                    baseValues(entity, operator);
                    populatePlan(r, entity);
                    return entity;
                })
                .orElse(null);
    }

    private static void populatePlan(PlanChangeRequest r, ChangeRequest entity) {
        if (entity instanceof CreatePlanRequest e) {
            e.setPlanKind(r.kind());
            e.setPlanName(r.name());
            e.setPlanPrice(r.price());
            e.setPlanStatus(r.status());
            e.setUploadSpeedMbps(r.uploadSpeedMbps());
            e.setDownloadSpeedMbps(r.downloadSpeedMbps());
            e.setNetworkGeneration(r.networkGeneration());
            e.setDataLimitGb(r.dataLimitGb());
            e.setCallCostPerMinute(r.callCostPerMinute());
            e.setSmsCostPerMessage(r.smsCostPerMessage());
        } else if (entity instanceof UpdatePlanRequest e) {
            e.setTargetPlanId(r.planId());
            e.setPlanKind(r.kind());
            e.setPlanName(r.name());
            e.setPlanPrice(r.price());
            e.setPlanStatus(r.status());
            e.setUploadSpeedMbps(r.uploadSpeedMbps());
            e.setDownloadSpeedMbps(r.downloadSpeedMbps());
            e.setNetworkGeneration(r.networkGeneration());
            e.setDataLimitGb(r.dataLimitGb());
            e.setCallCostPerMinute(r.callCostPerMinute());
            e.setSmsCostPerMessage(r.smsCostPerMessage());
        } else if (entity instanceof DeletePlanRequest e) {
            e.setTargetPlanId(r.planId());
        }
    }

    // --- OperatorChangeRequest → entity ---

    public ChangeRequest toEntity(OperatorChangeRequest request, UserOperator operator) {
        return Optional.ofNullable(request)
                .map(r -> {
                    UpdateOperatorRequest entity = new UpdateOperatorRequest();
                    baseValues(entity, operator);
                    entity.setTargetOperatorId(r.operatorId());
                    entity.setNewOperatorName(r.name());
                    return (ChangeRequest) entity;
                })
                .orElse(null);
    }

    // --- SubscriptionChangeRequest → entity ---

    public ChangeRequest toEntity(SubscriptionChangeRequest request, UserOperator operator) {
        return Optional.ofNullable(request)
                .map(r -> {
                    CreateSubscriptionRequest entity = new CreateSubscriptionRequest();
                    baseValues(entity, operator);
                    entity.setTargetPlanId(r.planId());
                    entity.setTargetUserId(r.userId());
                    return (ChangeRequest) entity;
                })
                .orElse(null);
    }

    // --- Approval / Rejection ---

    public ChangeRequest toApproved(ChangeRequest request, UserAdmin admin) {
        return Optional.ofNullable(request)
                .map(r -> {
                    r.setStatus(RequestStatus.APPROVED.create());
                    r.setReviewedBy(admin);
                    return r;
                })
                .orElse(null);
    }

    public ChangeRequest toRejected(ChangeRequest request, String reason, UserAdmin admin) {
        return Optional.ofNullable(request)
                .map(r -> {
                    r.setStatus(RequestStatus.REJECTED.create());
                    r.setRejectionReason(reason);
                    r.setReviewedBy(admin);
                    return r;
                })
                .orElse(null);
    }

    // --- ChangeRequest entity → downstream request DTOs ---

    public PlanRequest toPlanRequest(CreatePlanRequest r) {
        return Optional.ofNullable(r)
                .map(e
                     -> new PlanRequest(
                             e.getPlanKind(),
                             e.getPlanName(),
                             e.getRequestedBy().getOperator().getId(),
                             e.getPlanPrice(),
                             e.getPlanStatus(),
                             e.getUploadSpeedMbps(),
                             e.getDownloadSpeedMbps(),
                             e.getDataLimitGb(),
                             e.getCallCostPerMinute(),
                             e.getSmsCostPerMessage(),
                             e.getNetworkGeneration(),
                             null,
                             null
                     ))
                .orElse(null);
    }

    public PlanRequest toPlanRequest(UpdatePlanRequest r) {
        return Optional.ofNullable(r)
                .map(e
                     -> new PlanRequest(
                             e.getPlanKind(),
                             e.getPlanName(),
                             e.getRequestedBy().getOperator().getId(),
                             e.getPlanPrice(),
                             e.getPlanStatus(),
                             e.getUploadSpeedMbps(),
                             e.getDownloadSpeedMbps(),
                             e.getDataLimitGb(),
                             e.getCallCostPerMinute(),
                             e.getSmsCostPerMessage(),
                             e.getNetworkGeneration(),
                             null,
                             null
                     ))
                .orElse(null);
    }

    public OperatorRequest toOperatorRequest(UpdateOperatorRequest r) {
        return Optional.ofNullable(r).map(e -> new OperatorRequest(null, e.getNewOperatorName(), null)).orElse(null);
    }

    public SubscriptionRequest toSubscriptionRequest(CreateSubscriptionRequest r) {
        return Optional.ofNullable(r)
                .map(e -> new SubscriptionRequest(e.getRequestedBy().getOperator().getId(), e.getTargetPlanId(), e.getTargetUserId(), SubscriptionStatus.ACTIVE))
                .orElse(null);
    }

    // --- ChangeRequest → ChangeRequestResponse ---

    public ChangeRequestResponse toResponse(ChangeRequest request) {
        if (request instanceof CreatePlanRequest r)
            return ChangeRequestResponse.forCreatePlan(r);
        if (request instanceof UpdatePlanRequest r)
            return ChangeRequestResponse.forUpdatePlan(r);
        if (request instanceof DeletePlanRequest r)
            return ChangeRequestResponse.forDeletePlan(r);
        if (request instanceof UpdateOperatorRequest r)
            return ChangeRequestResponse.forUpdateOperator(r);
        if (request instanceof CreateSubscriptionRequest r)
            return ChangeRequestResponse.forCreateSubscription(r);
        return null;
    }
}
