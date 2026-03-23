package se.lexicon.subscriptionapi.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.entity.user.UserOperator;
import se.lexicon.subscriptionapi.dto.request.OperatorChangeRequest;
import se.lexicon.subscriptionapi.dto.request.PlanChangeRequest;
import se.lexicon.subscriptionapi.dto.request.SubscriptionChangeRequest;
import se.lexicon.subscriptionapi.dto.response.ChangeRequestResponse;
import se.lexicon.subscriptionapi.exception.BusinessRuleException;
import se.lexicon.subscriptionapi.exception.InvalidRequestException;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.RequestMapper;
import se.lexicon.subscriptionapi.repository.RequestRepository;
import se.lexicon.subscriptionapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    private Optional<UserOperator> resolveOperator(String email) {
        return Optional.ofNullable(email).flatMap(userRepository::findByEmail).filter(UserOperator.class::isInstance)
                .map(UserOperator.class::cast);
    }

    @Override
    @Transactional
    public ChangeRequestResponse submitPlan(PlanChangeRequest request, String operatorEmail) {
        return resolveOperator(operatorEmail)
                .flatMap(operator -> Optional.ofNullable(request).map(r -> requestMapper.toEntity(r, operator)))
                .map(requestRepository::save)
                .map(requestMapper::toResponse)
                .orElseThrow(
                        () -> request == null
                                ? new InvalidRequestException("auto")
                                : resolveOperator(operatorEmail).isEmpty()
                                        ? new ResourceNotFoundException("auto")
                                        : new BusinessRuleException("auto"));
    }

    @Override
    @Transactional
    public ChangeRequestResponse submitOperator(OperatorChangeRequest request, String operatorEmail) {
        return resolveOperator(operatorEmail)
                .flatMap(operator -> Optional.ofNullable(request).map(r -> requestMapper.toEntity(r, operator)))
                .map(requestRepository::save)
                .map(requestMapper::toResponse)
                .orElseThrow(
                        () -> request == null
                                ? new InvalidRequestException("auto")
                                : resolveOperator(operatorEmail).isEmpty()
                                        ? new ResourceNotFoundException("auto")
                                        : new BusinessRuleException("auto"));
    }

    @Override
    @Transactional
    public ChangeRequestResponse submitSubscription(SubscriptionChangeRequest request, String operatorEmail) {
        return resolveOperator(operatorEmail)
                .flatMap(operator -> Optional.ofNullable(request).map(r -> requestMapper.toEntity(r, operator)))
                .map(requestRepository::save)
                .map(requestMapper::toResponse)
                .orElseThrow(
                        () -> request == null
                                ? new InvalidRequestException("auto")
                                : resolveOperator(operatorEmail).isEmpty()
                                        ? new ResourceNotFoundException("auto")
                                        : new BusinessRuleException("auto"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChangeRequestResponse> getMyRequests(String operatorEmail) {
        return resolveOperator(operatorEmail).map(u -> requestRepository.findByRequestedBy_Id(u.getId())).stream()
                .flatMap(List::stream).map(requestMapper::toResponse).toList();
    }
}
