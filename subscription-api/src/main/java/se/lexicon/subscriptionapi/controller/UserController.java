package se.lexicon.subscriptionapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.dto.request.UserRequest;
import se.lexicon.subscriptionapi.dto.response.UserResponse;
import se.lexicon.subscriptionapi.service.UserService;

import java.util.List;

@Tag(name = "Users", description = "User endpoints (public register; other endpoints require JWT).")
@RestController
@RequestMapping("/api/v1/Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService UserService;

    @PostMapping
    @SecurityRequirements // Public (no auth)
    @Operation(
            summary = "Register a new User",
            description = "Public endpoint. Creates a User account.\n\nRoles: Public"
    )
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get User by ID",
            description = "Requires JWT.\n\nRoles: USER, ADMIN",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserResponse> read(@PathVariable Long id) {
        return ResponseEntity.ok(UserService.read(id));
    }

    @GetMapping("/email/{email}")
    @Operation(
            summary = "Get User by email",
            description = "Requires JWT.\n\nRoles: USER, ADMIN",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserResponse> getEmail(@PathVariable String email) {
        return ResponseEntity.ok(UserService.getEmail(email));
    }

    @GetMapping
    @Operation(
            summary = "Get all Users",
            description = "Requires JWT.\n\nRoles: USER, ADMIN",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(UserService.getAll());
    }

    // @PutMapping("/{id}/profile")
    // @Operation(
    //         summary = "Update User profile",
    //         description = "Requires JWT.\n\nRoles: USER, ADMIN",
    //         security = @SecurityRequirement(name = "bearerAuth")
    // )
    
    // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    // public ResponseEntity<UserResponse> updateProfile(
    //         @PathVariable Long id,
    //         @Valid @RequestBody UserRequest detailRequest) {
    //     return ResponseEntity.ok(UserService.update(id, detailRequest));
    // }
}
