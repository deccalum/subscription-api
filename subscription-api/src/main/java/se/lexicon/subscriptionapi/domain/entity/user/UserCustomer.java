package se.lexicon.subscriptionapi.domain.entity.user;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.domain.entity.User;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@DiscriminatorValue("CUSTOMER")
public class UserCustomer extends User {
    
    @Column
    private String address;

    @Column
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subscription> subscriptions;

    private String preferences;
}