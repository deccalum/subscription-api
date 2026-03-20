package se.lexicon.subscriptionapi.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.entity.user.UserOperator;
import se.lexicon.subscriptionapi.dto.request.*;
import se.lexicon.subscriptionapi.dto.response.ChangeRequestResponse;
import se.lexicon.subscriptionapi.mapper.RequestMapper;
import se.lexicon.subscriptionapi.repository.ChangeRequestRepository;
import se.lexicon.subscriptionapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ChangeRequestServiceImpl implements ChangeRequestService {
    private final ChangeRequestRepository changeRequestRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public ChangeRequestResponse submitCreatePlan(CreatePlanChangeRequest dto, String operatorEmail) {
        return Optional.ofNullable(operatorEmail)
                .map(this::resolveOperator)
                .map(operator -> requestMapper.toCreatePlanRequest(dto, operator))
                .map(changeRequestRepository::save)
                .map(requestMapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public ChangeRequestResponse submitUpdatePlan(UpdatePlanChangeRequest dto, String operatorEmail) {
        return Optional.ofNullable(operatorEmail)
                .map(this::resolveOperator)
                .map(operator -> requestMapper.toUpdatePlanRequest(dto, operator))
                .map(changeRequestRepository::save)
                .map(requestMapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public ChangeRequestResponse submitDeletePlan(DeletePlanChangeRequest dto, String operatorEmail) {
        return Optional.ofNullable(operatorEmail)
                .map(this::resolveOperator)
                .map(operator -> requestMapper.toDeletePlanRequest(dto, operator))
                .map(changeRequestRepository::save)
                .map(requestMapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public ChangeRequestResponse submitUpdateOperator(UpdateOperatorChangeRequest dto, String operatorEmail) {
        return Optional.ofNullable(operatorEmail)
                .map(this::resolveOperator)
                .map(operator -> requestMapper.toUpdateOperatorRequest(dto, operator))
                .map(changeRequestRepository::save)
                .map(requestMapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public ChangeRequestResponse submitCreateSubscription(CreateSubscriptionChangeRequest dto, String operatorEmail) {
        return Optional.ofNullable(operatorEmail)
                .map(this::resolveOperator)
                .map(operator -> requestMapper.toCreateSubscriptionRequest(dto, operator))
                .map(changeRequestRepository::save)
                .map(requestMapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChangeRequestResponse> getMyRequests(String operatorEmail) {
        return Optional.ofNullable(operatorEmail)
                .map(this::resolveOperator)
                .map(UserOperator::getId)
                .map(changeRequestRepository::findByRequestedBy_Id)
                .orElse(List.of())
                .stream()
                .map(requestMapper::toResponse)
                .toList();
    }

    private UserOperator resolveOperator(String email) {
        return Optional.ofNullable(email).flatMap(userRepository::findByEmail).filter(UserOperator.class ::isInstance).map(UserOperator.class ::cast).orElse(null);
    }
}
