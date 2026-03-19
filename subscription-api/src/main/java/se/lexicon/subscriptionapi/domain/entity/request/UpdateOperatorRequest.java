package se.lexicon.subscriptionapi.domain.entity.request;

import jakarta.persistence.*;
import lombok.*;
import se.lexicon.subscriptionapi.domain.entity.ChangeRequest;

@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("UPDATE_OPERATOR")
public class UpdateOperatorRequest extends ChangeRequest {

    private Long targetOperatorId;
    private String newOperatorName;
}
