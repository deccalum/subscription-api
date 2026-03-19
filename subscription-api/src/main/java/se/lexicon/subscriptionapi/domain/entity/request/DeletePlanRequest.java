package se.lexicon.subscriptionapi.domain.entity.request;

import jakarta.persistence.*;
import lombok.*;
import se.lexicon.subscriptionapi.domain.entity.ChangeRequest;

@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("DELETE_PLAN")
public class DeletePlanRequest extends ChangeRequest {

    private Long targetPlanId;
}
