package se.lexicon.subscriptionapi.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import se.lexicon.subscriptionapi.domain.entity.User;

@Entity
@DiscriminatorValue("ADMIN")
@Getter @Setter
@NoArgsConstructor
public class UserAdmin extends User {
    // Admin-specific settings, e.g., require2FA, specialized permissions
}