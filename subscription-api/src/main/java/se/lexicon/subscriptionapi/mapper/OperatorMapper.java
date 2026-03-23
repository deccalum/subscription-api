package se.lexicon.subscriptionapi.mapper;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.dto.request.OperatorRequest;
import se.lexicon.subscriptionapi.dto.response.OperatorResponse;
import se.lexicon.subscriptionapi.dto.response.PlanSummary;

/**
 * Maps between {@link Operator} ↔ {@link OperatorRequest} / {@link OperatorResponse}.
 *
 * <p>Uses {@link java.util.Optional} to express nullable input explicitly —
 * each method returns {@code null} on absent input rather than throwing,
 * keeping null-handling declarative and consistent across the mapping layer.</p>
 *
 * <p>Plans are mapped as {@link PlanSummary} snapshots — see {@link PlanMapper}
 * for full plan mapping.</p>
 *
 * <ul>
 *   <li>{@link #toEntity(OperatorRequest)} — request → persistable entity</li>
 *   <li>{@link #toResponse(Operator)} — entity → API response</li>
 * </ul>
 */
@Component
public class OperatorMapper {

    public Operator toEntity(OperatorRequest request) {
        return Optional.ofNullable(request)
        .map(r -> Operator.builder()
        .name(r.name())
        .countryCode(r.countryCode())
        .writeInstant(r.writeInstant())
        .build())
        .orElse(null);
    }

    public OperatorResponse toResponse(Operator operator) {
        return Optional.ofNullable(operator)
            .map(o -> new OperatorResponse(
                o.getId(),
                o.getName(),
                o.getCountryCode(),
                o.getPlans().stream()
                    .map(p -> new PlanSummary(
                        p.getId(), 
                        p.getName(), 
                        p.getPrice(), 
                        p.getKind(), 
                        p.getStatus()))
                    .collect(Collectors.toSet())
            ))
            .orElse(null);
    }
}
