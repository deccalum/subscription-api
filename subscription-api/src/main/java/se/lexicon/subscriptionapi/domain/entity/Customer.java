package se.lexicon.subscriptionapi.domain.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import se.lexicon.subscriptionapi.domain.constant.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String lastName;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min = 6)
    @ToString.Exclude
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_roles", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_detail_id")
    @ToString.Exclude
    private CustomerDetail customerDetail;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Subscription> subscriptions = new HashSet<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;
}
