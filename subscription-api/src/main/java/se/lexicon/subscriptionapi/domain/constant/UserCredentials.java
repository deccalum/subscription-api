package se.lexicon.subscriptionapi.domain.constant;

import java.util.function.Supplier;
import se.lexicon.subscriptionapi.domain.entity.User;
import se.lexicon.subscriptionapi.domain.entity.user.UserAdmin;
import se.lexicon.subscriptionapi.domain.entity.user.UserCustomer;
import se.lexicon.subscriptionapi.domain.entity.user.UserOperator;

public enum UserCredentials {
    ROLE_USER(UserCustomer::new),
    ROLE_OPERATOR(UserOperator::new),
    ROLE_ADMIN(UserAdmin::new);

    private final Supplier<User> factory;

    UserCredentials(Supplier<User> factory) {
        this.factory = factory;
    }

    public User create() {
        return factory.get();
    }
}
