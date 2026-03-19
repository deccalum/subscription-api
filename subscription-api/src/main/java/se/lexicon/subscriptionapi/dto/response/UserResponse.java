package se.lexicon.subscriptionapi.dto.response;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String address,
        String phoneNumber,
        String preferences
) {}
