package se.lexicon.subscriptionapi.dto.response;

import java.time.Instant;
import java.util.Set;
import se.lexicon.subscriptionapi.domain.constant.UserCredentials;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.domain.entity.User;


public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        UserCredentials credentials,
        Instant writeInstant,
        Instant deleteInstant,
        Instant lastLoginInstant,
        String lastLoginIp,
        Operator operator,
        String address,
        String phoneNumber,
        String preferences
) {
    public static UserResponse admin(User u) {
        return new UserResponse(
                u.getId(),
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getRoles().stream().findFirst().orElse(null),
                u.getWriteInstant(),
                u.getDeleteInstant(),
                u.getLastLoginInstant(),
                u.getLastLoginIp(),
                null,
                null,
                null,
                null
        );
    }

    public static UserResponse customer(User u, String address, String phoneNumber, Set<Subscription> subscriptions, String preferences) {
        return new UserResponse(
                u.getId(),
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getRoles().stream().findFirst().orElse(null),
                u.getWriteInstant(),
                u.getDeleteInstant(),
                u.getLastLoginInstant(),
                u.getLastLoginIp(),
                null,
                address,
                phoneNumber,
                preferences
        );
    }

    public static UserResponse operator(User u, Operator operator) {
        return new UserResponse(
                u.getId(),
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getRoles().stream().findFirst().orElse(null),
                u.getWriteInstant(),
                u.getDeleteInstant(),
                u.getLastLoginInstant(),
                u.getLastLoginIp(),
                operator,
                null,
                null,
                null
        );
    }
}
