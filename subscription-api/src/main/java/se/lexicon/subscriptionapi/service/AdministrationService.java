package se.lexicon.subscriptionapi.service;

import java.util.List;
import se.lexicon.subscriptionapi.domain.constant.RequestStatus;
import se.lexicon.subscriptionapi.dto.response.ChangeRequestResponse;

public interface AdministrationService {
    ChangeRequestResponse approveRequest(Long id, String adminEmail);
    ChangeRequestResponse rejectRequest(Long id, String reason, String adminEmail);
    List<ChangeRequestResponse> getByStatus(RequestStatus status);
    List<ChangeRequestResponse> getPending();
}
