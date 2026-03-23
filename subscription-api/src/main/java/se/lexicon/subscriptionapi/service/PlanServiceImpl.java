package se.lexicon.subscriptionapi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.constant.PlanStatus;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;
import se.lexicon.subscriptionapi.exception.InvalidRequestException;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.PlanMapper;
import se.lexicon.subscriptionapi.repository.OperatorRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final OperatorRepository operatorRepository;
    private final PlanMapper planMapper;

    @Override
    @Transactional
    public PlanResponse create(PlanRequest request) {
        return Optional.ofNullable(request)
                .flatMap(req -> Optional.ofNullable(req.operator()).flatMap(operatorRepository::findById)
                        .map(operator -> planMapper.toEntity(req, req.kind().create(), operator)))
                .map(planRepository::save)
                .map(planMapper::toResponse)
                .orElseThrow(() -> request == null || request.operator() == null
                        ? new InvalidRequestException("auto")
                        : new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional(readOnly = true)
    public PlanResponse read(Long id) {
        return planRepository.findById(id).map(planMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional
    public PlanResponse update(Long id, PlanRequest request) {
        return planRepository.findById(id)
                .flatMap(existing -> Optional.ofNullable(request).map(req -> planMapper.toEntity(req, existing, null))
                        .map(planRepository::save))
                .map(planMapper::toResponse)
                .orElseThrow(() -> request == null
                        ? new InvalidRequestException("auto")
                        : new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional.ofNullable(id).filter(planRepository::existsById).ifPresentOrElse(planRepository::deleteById, () -> {
            throw new ResourceNotFoundException("auto");
        });
    }

    @Override
    @Transactional(readOnly = true)
    public PlanResponse getByName(String name) {
        return planRepository.findByNameIgnoreCase(name).map(planMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findByOperatorName(String operatorName) {
        return planRepository.findAllByOperatorNameIgnoreCase(operatorName).stream().map(planMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findByOperatorId(Long operatorId) {
        return planRepository.findByOperatorId(operatorId).stream().map(planMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return planRepository.findByPriceBetween(minPrice, maxPrice).stream().map(planMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findByStatus(PlanStatus status) {
        return planRepository.findByStatus(status).stream().map(planMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByOperatorId(Long operatorId) {
        return planRepository.existsByOperatorId(operatorId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByOperatorIdAndStatus(Long operatorId, PlanStatus status) {
        return planRepository.countByOperatorIdAndStatus(operatorId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findAllInternetPlans() {
        return planRepository.findAllInternetPlans().stream().map(planMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findAllCellularPlans() {
        return planRepository.findAllCellularPlans().stream().map(planMapper::toResponse).collect(Collectors.toList());
    }
}
