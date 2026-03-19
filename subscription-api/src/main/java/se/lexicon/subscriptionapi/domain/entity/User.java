package se.lexicon.subscriptionapi.domain.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import se.lexicon.subscriptionapi.domain.constant.UserCredentials;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public abstract class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_credentials", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "credential")
    @Enumerated(EnumType.STRING)
    private Set<UserCredentials> roles = new HashSet<>();

    @Column(updatable = false)
    @CreationTimestamp
    private Instant writeInstant;
    
    private Instant deleteInstant;

    @Column(name = "last_login_instant")
    private Instant lastLoginInstant;

    @Column(name = "last_login_ip")
    private String lastLoginIp;
}
