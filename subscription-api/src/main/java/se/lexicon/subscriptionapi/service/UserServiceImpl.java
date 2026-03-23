package se.lexicon.subscriptionapi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.constant.UserCredentials;
import se.lexicon.subscriptionapi.dto.request.UserRequest;
import se.lexicon.subscriptionapi.dto.response.UserResponse;
import se.lexicon.subscriptionapi.exception.InvalidRequestException;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.UserMapper;
import se.lexicon.subscriptionapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository UserRepository;
    private final UserMapper UserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse create(UserRequest request) {
        return Optional.ofNullable(request)
                .map(req -> UserMapper.toEntity(req, req.credentials().create()))
                .map(User -> {
                    User.setPassword(passwordEncoder.encode(User.getPassword()));
                    User.setRoles(new HashSet<>(Set.of(request.credentials())));
                    return User;
                })
                .map(UserRepository::save)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new InvalidRequestException("auto"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse read(Long id) {
        return UserRepository.findById(id).map(UserMapper::toResponse).orElseThrow(() -> new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        return UserRepository.findById(id)
                .flatMap(existing -> Optional.ofNullable(request).map(r -> UserMapper.toEntity(r, existing)))
                .map(User -> {
                    User.setPassword(passwordEncoder.encode(User.getPassword()));
                    return User;
                })
                .map(UserRepository::save)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> request == null ? new InvalidRequestException("auto") : new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional.ofNullable(id).filter(UserRepository::existsById).ifPresentOrElse(UserRepository::deleteById, () -> { throw new ResourceNotFoundException("auto"); });
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return UserRepository.findAll().stream().map(UserMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCredentials(UserCredentials credential) {
        return UserRepository.findByRolesContaining(credential).map(UserMapper::toResponse).orElseThrow(() -> new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getEmail(String email) {
        return UserRepository.findByEmail(email).map(UserMapper::toResponse).orElseThrow(() -> new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getName(String name) {
        return UserRepository.findByFirstNameIgnoreCase(name).map(UserMapper::toResponse).orElseThrow(() -> new ResourceNotFoundException("auto"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return UserRepository.existsByEmail(email);
    }
}
