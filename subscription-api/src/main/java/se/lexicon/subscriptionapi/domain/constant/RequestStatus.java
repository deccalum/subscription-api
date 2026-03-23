package se.lexicon.subscriptionapi.domain.constant;

import java.util.function.Supplier;

public enum RequestStatus {
    PENDING(RequestStatus::pending),
    APPROVED(RequestStatus::approved),
    REJECTED(RequestStatus::rejected);

    private final Supplier<RequestStatus> factory;

    RequestStatus(Supplier<RequestStatus> factory) {
        this.factory = factory;
    }

    public RequestStatus create() {
        return factory.get();
    }

    private static RequestStatus pending() {
        return PENDING;
    }

    private static RequestStatus approved() {
        return APPROVED;
    }

    private static RequestStatus rejected() {
        return REJECTED;
    }
}