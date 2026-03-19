package se.lexicon.subscriptionapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.subscriptionapi.domain.constant.RequestStatus;
import se.lexicon.subscriptionapi.domain.entity.ChangeRequest;

@Repository
public interface ChangeRequestRepository extends JpaRepository<ChangeRequest, Long> {
    List<ChangeRequest> findByStatus(RequestStatus status);
    List<ChangeRequest> findByRequestedBy_Id(Long operatorUserId);
}
