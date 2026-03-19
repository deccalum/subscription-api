package se.lexicon.subscriptionapi.service;

import java.util.List;
import se.lexicon.subscriptionapi.dto.request.*;
import se.lexicon.subscriptionapi.dto.response.ChangeRequestResponse;

public interface ChangeRequestService {
    ChangeRequestResponse submitCreatePlan(CreatePlanChangeRequest dto, String operatorEmail);
    ChangeRequestResponse submitUpdatePlan(UpdatePlanChangeRequest dto, String operatorEmail);
    ChangeRequestResponse submitDeletePlan(DeletePlanChangeRequest dto, String operatorEmail);
    ChangeRequestResponse submitUpdateOperator(UpdateOperatorChangeRequest dto, String operatorEmail);
    ChangeRequestResponse submitCreateSubscription(CreateSubscriptionChangeRequest dto, String operatorEmail);
    List<ChangeRequestResponse> getMyRequests(String operatorEmail);
}
