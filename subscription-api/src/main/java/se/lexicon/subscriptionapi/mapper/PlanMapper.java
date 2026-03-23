package se.lexicon.subscriptionapi.mapper;

import java.util.Optional;
import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.domain.entity.plan.PlanCellular;
import se.lexicon.subscriptionapi.domain.entity.plan.PlanInternet;
import se.lexicon.subscriptionapi.domain.entity.plan.PlanSatellite;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;


/**
 * Maps between {@link Plan} ↔ {@link PlanRequest} / {@link PlanResponse}.
 *
 * <p>Uses {@link java.util.Optional} to express nullable input explicitly —
 * each method returns {@code null} on absent input rather than throwing,
 * keeping null-handling declarative and consistent across the mapping layer.</p>
 *
 *
 * <ul>
 *   <li>{@link #toEntity(PlanRequest, Plan, Operator)} — request → persistable entity</li>
 *   <li>{@link #toResponse(Plan)} — entity → API response</li>
 * </ul>
 */
@Component
public class PlanMapper {
    private void baseValues(Plan plan, PlanRequest r, Operator operator) {
        plan.setName(r.name());
        plan.setPrice(r.price());
        plan.setStatus(r.status());
        plan.setOperator(operator);
    }

    public Plan toEntity(PlanRequest request, Plan existing, Operator operator) {
        return Optional.ofNullable(request)
                .map(r -> {
                    Plan plan = Optional.ofNullable(existing).orElseGet(r.kind()::create);
                    baseValues(plan, r, Optional.ofNullable(operator).orElseGet(plan::getOperator));
                    return plan;
                })
                .orElse(null);
    }

    public PlanResponse toResponse(Plan plan) {
        if (plan instanceof PlanInternet p)
            return toResponse(p);
        if (plan instanceof PlanCellular p)
            return toResponse(p);
        if (plan instanceof PlanSatellite p)
            return toResponse(p);
        return null;
    }

    public PlanResponse toResponse(PlanInternet p) {
        return Optional.ofNullable(p)
                .map(plan -> {
                    Integer upload = plan.getSpeed() != null ? plan.getSpeed().getUpload() : null;
                    Integer download = plan.getSpeed() != null ? plan.getSpeed().getDownload() : null;
                    return PlanResponse.internet(plan, upload, download);
                })
                .orElse(null);
    }

    public PlanResponse toResponse(PlanCellular p) {
        return Optional.ofNullable(p)
                .map(plan -> PlanResponse.cellular(plan, plan.getNetworkGeneration(), plan.getDataLimitGb(), plan.getCallCostPerMinute(), plan.getSmsCostPerMessage()))
                .orElse(null);
    }

    public PlanResponse toResponse(PlanSatellite p) {
        return Optional.ofNullable(p).map(plan -> PlanResponse.satellite(plan, plan.getCoverage(), plan.getMHz())).orElse(null);
    }
}
