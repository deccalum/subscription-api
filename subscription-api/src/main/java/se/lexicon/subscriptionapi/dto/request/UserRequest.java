package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.ToString;
import se.lexicon.subscriptionapi.domain.constant.UserCredentials;
import se.lexicon.subscriptionapi.validation.UniqueEmail;

public record UserRequest(
        @Positive(message = "{invalidId}") Long id,

        @NotNull(message = "{blank}") UserCredentials credentials,

        @NotBlank(message = "{blank}") @Email(message = "{invalidEmail}") @Size(min = 5, max = 100, message = "{invalidLength}") @UniqueEmail(message = "{duplicateEmail}")
        String email,

        @NotBlank(message = "{blank}") @Size(max = 50, message = "{invalidLength}") String firstName,

        @NotBlank(message = "{blank}") @Size(max = 50, message = "{invalidLength}") String lastName,

        @NotBlank(message = "{blank}") @Size(min = 8, message = "{invalidLength}") @ToString.Exclude String password,

        Instant writeInstant,

        @Future(message = "{invalidInstant}") Instant deleteInstant,

        @PastOrPresent(message = "{invalidInstant}") Instant lastLoginInstant,

        String lastLoginIp, // set server-side on login, not client-validated

        @Size(max = 200, message = "{invalidLength}") String address,

        @Pattern(regexp = "^\\+?[0-9\\s-]{7,20}$", message = "{invalidNumber}") String phoneNumber,

        String preferences
) {}
