package se.lexicon.subscriptionapi.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.User;

@Entity
@DiscriminatorValue("OPERATOR")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserOperator extends User {
    // extend id somehow
    // @Override
    // public Long id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    private Operator operator; // Links to their company/employer
    // Operator-specific settings, e.g., permissions to manage subscriptions, view reports, etc.
}