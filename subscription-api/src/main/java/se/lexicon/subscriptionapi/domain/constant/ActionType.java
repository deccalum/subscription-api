package se.lexicon.subscriptionapi.domain.constant;

import java.util.function.Supplier;
import se.lexicon.subscriptionapi.domain.entity.ChangeRequest;
import se.lexicon.subscriptionapi.domain.entity.request.CreatePlanRequest;
import se.lexicon.subscriptionapi.domain.entity.request.CreateSubscriptionRequest;
import se.lexicon.subscriptionapi.domain.entity.request.DeletePlanRequest;
import se.lexicon.subscriptionapi.domain.entity.request.UpdateOperatorRequest;
import se.lexicon.subscriptionapi.domain.entity.request.UpdatePlanRequest;

public enum ActionType {
    CREATE(CreatePlanRequest::new),
    UPDATE(UpdatePlanRequest::new),
    DELETE(DeletePlanRequest::new),

    // Persisted discriminator values on ChangeRequest inheritance.
    CREATE_PLAN(CreatePlanRequest::new),
    UPDATE_PLAN(UpdatePlanRequest::new),
    DELETE_PLAN(DeletePlanRequest::new),
    UPDATE_OPERATOR(UpdateOperatorRequest::new),
    CREATE_SUBSCRIPTION(CreateSubscriptionRequest::new);

    private final Supplier<ChangeRequest> factory;

    ActionType(Supplier<ChangeRequest> factory) {
        this.factory = factory;
    }

    public ChangeRequest create() {
        return factory.get();
    }
}
