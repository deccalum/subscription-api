package se.lexicon.subscriptionapi.mapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.domain.entity.User;
import se.lexicon.subscriptionapi.domain.entity.user.UserAdmin;
import se.lexicon.subscriptionapi.domain.entity.user.UserCustomer;
import se.lexicon.subscriptionapi.domain.entity.user.UserOperator;
import se.lexicon.subscriptionapi.dto.request.UserRequest;
import se.lexicon.subscriptionapi.dto.response.UserResponse;

@Component
public class UserMapper {
    private void baseValues(User user, UserRequest r) {
        user.setEmail(r.email());
        user.setFirstName(r.firstName());
        user.setLastName(r.lastName());
        user.setPassword(r.password());
        user.setRoles(new HashSet<>(Set.of(r.credentials())));
        user.setWriteInstant(r.writeInstant());
        user.setDeleteInstant(r.deleteInstant());
        user.setLastLoginInstant(r.lastLoginInstant());
        user.setLastLoginIp(r.lastLoginIp());
    }

    public User toEntity(UserRequest request, User existing) {
        return Optional.ofNullable(request)
                .map(r -> {
                    User user = Optional.ofNullable(existing).orElseGet(r.credentials()::create);
                    baseValues(user, r);
                    return user;
                })
                .orElse(null);
    }

    public UserResponse toResponse(User user) {
        if (user instanceof UserAdmin u)
            return toResponse(u);
        if (user instanceof UserCustomer u)
            return toResponse(u);
        if (user instanceof UserOperator u)
            return toResponse(u);
        return null;
    }

    public UserResponse toResponse(UserAdmin u) {
        return Optional.ofNullable(u).map(user -> UserResponse.admin(user)).orElse(null);
    }

    public UserResponse toResponse(UserCustomer u) {
        return Optional.ofNullable(u).map(user -> UserResponse.customer(user, u.getAddress(), u.getPhoneNumber(), u.getSubscriptions(), u.getPreferences())).orElse(null);
    }

    public UserResponse toResponse(UserOperator u) {
        return Optional.ofNullable(u).map(user -> UserResponse.operator(user, u.getOperator())).orElse(null);
    }
}
