package se.lexicon.subscriptionapi.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.dto.request.OperatorRequest;
import se.lexicon.subscriptionapi.dto.response.OperatorResponse;
import se.lexicon.subscriptionapi.exception.InvalidRequestException;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.OperatorMapper;
import se.lexicon.subscriptionapi.repository.OperatorRepository;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {
    private final OperatorRepository operatorRepository;
    private final OperatorMapper operatorMapper;

    @Override
    @Transactional
    public OperatorResponse create(OperatorRequest request) {
        return Optional.ofNullable(request)
                .map(operatorMapper::toEntity)
                .map(operatorRepository::save)
                .map(operatorMapper::toResponse)
                .orElseThrow(() -> new InvalidRequestException("auto"));
    }

    @Override
    @Transactional(readOnly = true)
    public OperatorResponse read(Long id) {
        return operatorRepository.findById(id).map(operatorMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional
    public OperatorResponse update(Long id, OperatorRequest request) {
        return operatorRepository.findById(id)
                .flatMap(existing -> Optional.ofNullable(request).map(operatorMapper::toEntity)
                        .map(operatorRepository::save))
                .map(operatorMapper::toResponse)
                .orElseThrow(() -> request == null
                        ? new InvalidRequestException("auto")
                        : new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional.ofNullable(id).filter(operatorRepository::existsById).ifPresentOrElse(operatorRepository::deleteById,
                () -> {
                    throw new ResourceNotFoundException("auto");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public OperatorResponse getName(String name) {
        return operatorRepository.findByName(name).map(operatorMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional(readOnly = true)
    public OperatorResponse getNameIgnoreCase(String name) {
        return operatorRepository.findByNameIgnoreCase(name).map(operatorMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperatorResponse> getAll() {
        return operatorRepository.findAll().stream().map(operatorMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return operatorRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNameIgnoreCase(String name) {
        return operatorRepository.existsByNameIgnoreCase(name);
    }
}
