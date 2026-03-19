package se.lexicon.subscriptionapi.domain.entity.request;

import jakarta.persistence.*;
import lombok.*;
import se.lexicon.subscriptionapi.domain.entity.ChangeRequest;

@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("CREATE_SUBSCRIPTION")
public class CreateSubscriptionRequest extends ChangeRequest {

    private Long targetPlanId;
    private Long targetUserId;
}
