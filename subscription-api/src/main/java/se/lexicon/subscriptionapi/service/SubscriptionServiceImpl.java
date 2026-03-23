package se.lexicon.subscriptionapi.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;
import se.lexicon.subscriptionapi.exception.BusinessRuleException;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.SubscriptionMapper;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.repository.SubscriptionRepository;
import se.lexicon.subscriptionapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public SubscriptionResponse create(SubscriptionRequest request) {
        return Optional.ofNullable(request)
                .flatMap(
                        req -> planRepository.findById(req.planId())
                                .filter(plan -> plan.getOperator().getId().equals(req.operatorId()))
                                .flatMap(plan -> userRepository.findById(req.userId())
                                        .map(user -> subscriptionMapper.toEntity(req, plan, user))))
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toResponse)
                .orElseThrow(() -> new BusinessRuleException("auto"));
    }

    @Override
    @Transactional
    public SubscriptionResponse read(Long id) {
        return subscriptionRepository.findById(id).map(subscriptionMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional
    public SubscriptionResponse update(Long id, SubscriptionRequest request) {
        return subscriptionRepository.findById(id)
                .flatMap(existing -> Optional.ofNullable(request).map(req -> {
                    existing.setStatus(req.status());
                    return existing;
                }))
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toResponse)
                .orElseThrow(() -> request == null
                        ? new BusinessRuleException("auto")
                        : new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional.ofNullable(id).filter(subscriptionRepository::existsById)
                .ifPresentOrElse(subscriptionRepository::deleteById, () -> {
                    throw new ResourceNotFoundException("auto");
                });
    }

    @Override
    @Transactional
    public List<SubscriptionResponse> getAll() {
        return subscriptionRepository.findAll().stream().map(subscriptionMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public List<SubscriptionResponse> getUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId).stream().map(subscriptionMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public List<SubscriptionResponse> getStatus(SubscriptionStatus status) {
        return subscriptionRepository.findByStatus(status).stream().map(subscriptionMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public List<SubscriptionResponse> findByUserName(String name) {
        return subscriptionRepository.findByUserFirstNameIgnoreCaseOrUserLastNameIgnoreCase(name, name).stream()
                .map(subscriptionMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public List<SubscriptionResponse> findByUserIdAndStatus(Long userId, SubscriptionStatus status) {
        return subscriptionRepository.findByUserIdAndStatus(userId, status).stream().map(subscriptionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public long countSubscriptions() {
        return subscriptionRepository.count();
    }
}
