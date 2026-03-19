package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.*;

public record RejectChangeRequest(
        @NotBlank(message = "{blank}")
        String reason
) {}
