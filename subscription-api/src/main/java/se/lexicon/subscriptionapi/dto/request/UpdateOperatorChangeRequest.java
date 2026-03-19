package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.*;

public record UpdateOperatorChangeRequest(
        @NotNull(message = "{required}")
        @Positive(message = "{invalidId}")
        Long targetOperatorId,

        @NotBlank(message = "{blank}")
        @Size(max = 100, message = "{invalidLength}")
        String newName
) {}
