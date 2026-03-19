package se.lexicon.subscriptionapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.domain.constant.RequestStatus;
import se.lexicon.subscriptionapi.dto.request.RejectChangeRequest;
import se.lexicon.subscriptionapi.dto.response.ChangeRequestResponse;
import se.lexicon.subscriptionapi.service.AdministrationService;

@Tag(name = "Admin", description = "Change request review endpoints.")
@RestController
@RequestMapping("/api/v1/admin/requests")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdministrationService administrationService;

    @GetMapping
    @Operation(summary = "Get change requests by status", description = "Requires JWT.\n\nRoles: ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChangeRequestResponse>> getByStatus(
            @RequestParam(defaultValue = "PENDING") RequestStatus status) {
        return ResponseEntity.ok(administrationService.getByStatus(status));
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "Approve a change request", description = "Executes the queued action and marks it APPROVED.\n\nRoles: ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChangeRequestResponse> approveRequest(
            @PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(administrationService.approveRequest(id, authentication.getName()));
    }

    @PostMapping("/{id}/reject")
    @Operation(summary = "Reject a change request", description = "Stores rejection reason and marks it REJECTED.\n\nRoles: ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChangeRequestResponse> rejectRequest(
            @PathVariable Long id,
            @Valid @RequestBody RejectChangeRequest body,
            Authentication authentication) {
        return ResponseEntity.ok(
                administrationService.rejectRequest(id, body.reason(), authentication.getName()));
    }
}