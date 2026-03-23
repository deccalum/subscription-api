package se.lexicon.subscriptionapi.service;

import java.util.List;
import se.lexicon.subscriptionapi.dto.request.*;
import se.lexicon.subscriptionapi.dto.response.ChangeRequestResponse;

public interface RequestService {
        ChangeRequestResponse submitPlan(PlanChangeRequest request, String operatorEmail);
        ChangeRequestResponse submitOperator(OperatorChangeRequest request, String operatorEmail);
        ChangeRequestResponse submitSubscription(SubscriptionChangeRequest request, String operatorEmail);
        List<ChangeRequestResponse> getMyRequests(String operatorEmail);
    }
