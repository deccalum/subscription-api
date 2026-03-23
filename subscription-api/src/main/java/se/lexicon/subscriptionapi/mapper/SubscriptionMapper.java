package se.lexicon.subscriptionapi.mapper;

import java.util.Optional;
import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.domain.entity.User;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;

@Component
public class SubscriptionMapper {

    public Subscription toEntity(SubscriptionRequest request, Plan plan, User user) {
        return Optional.ofNullable(request)
                .map(r -> Subscription.builder()
                        .status(r.status())
                        .plan(plan)
                        .user(user)
                        .build())
                .orElse(null);
    }

    public SubscriptionResponse toResponse(Subscription subscription) {
        return Optional.ofNullable(subscription)
                .map(s -> new SubscriptionResponse(
                    s.getId(),
                    s.getUser().getId(),
                    null, 
                    null, 
                    null, 
                    null, 
                    s.getStatus(),
                    s.getWriteInstant(),
                    s.getCancelInstant()
                ))
                .orElse(null);
    }
}
