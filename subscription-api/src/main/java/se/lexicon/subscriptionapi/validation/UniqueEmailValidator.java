package se.lexicon.subscriptionapi.validation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.repository.UserRepository;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private static final Pattern USER_ID_PATH = Pattern.compile(".*/api/v1/Users/(\\d+)$");

    private final UserRepository userRepository;
    private final HttpServletRequest request;

    public UniqueEmailValidator(UserRepository userRepository, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.request = request;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // other annotations (@NotBlank) handle emptiness
        }

        return userRepository.findByEmail(value)
                .map(existing -> isSameUserOnUpdate(existing.getId()))
                .orElse(true);
    }

    private boolean isSameUserOnUpdate(Long existingUserId) {
        if (existingUserId == null) {
            return false;
        }

        String method = request.getMethod();
        if (!"PUT".equalsIgnoreCase(method) && !"PATCH".equalsIgnoreCase(method)) {
            return false;
        }

        return currentUserIdFromPath().map(existingUserId::equals).orElse(false);
    }

    private Optional<Long> currentUserIdFromPath() {
        String uri = request.getRequestURI();
        Matcher matcher = USER_ID_PATH.matcher(uri);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(Long.parseLong(matcher.group(1)));
    }
}
