package se.lexicon.subscriptionapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.dto.request.*;
import se.lexicon.subscriptionapi.dto.response.ChangeRequestResponse;
import se.lexicon.subscriptionapi.dto.response.OperatorResponse;
import se.lexicon.subscriptionapi.service.ChangeRequestService;
import se.lexicon.subscriptionapi.service.OperatorService;

@Tag(name = "Operators", description = "Operator management endpoints.")
@RestController
@RequestMapping("/api/v1/operators")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OperatorController {

    private final OperatorService operatorService;
    private final ChangeRequestService changeRequestService;

    @PostMapping("/requests/plans")
    @Operation(summary = "Request plan creation", description = "Queues a CREATE_PLAN request for admin approval.\n\nRoles: OPERATOR")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<ChangeRequestResponse> submitCreatePlan(
            @Valid @RequestBody CreatePlanChangeRequest dto, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(changeRequestService.submitCreatePlan(dto, auth.getName()));
    }

    @PostMapping
    @Operation(summary = "Create operator", description = "Requires JWT.\n\nRoles: ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OperatorResponse> create(@Valid @RequestBody OperatorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(operatorService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get operator by ID", description = "Requires JWT.\n\nRoles: USER, ADMIN")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<OperatorResponse> read(@PathVariable Long id) {
        return ResponseEntity.ok(operatorService.read(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Search operator by name", description = "Requires JWT.\n\nRoles: USER, ADMIN")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<OperatorResponse> getName(@RequestParam String name) {
        return ResponseEntity.ok(operatorService.getName(name));
    }

    @GetMapping
    @Operation(summary = "Get all operators", description = "Requires JWT.\n\nRoles: USER, ADMIN")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<OperatorResponse>> get() {
        return ResponseEntity.ok(operatorService.getAll());
    }

        @PutMapping("/requests/plans/{planId}")
        @Operation(summary = "Request plan update", description = "Queues an UPDATE_PLAN request for admin approval.\n\nRoles: OPERATOR")
        @PreAuthorize("hasRole('OPERATOR')")
        public ResponseEntity<ChangeRequestResponse> submitUpdatePlan(
            @PathVariable Long planId,
            @Valid @RequestBody UpdatePlanChangeRequest dto,
            Authentication auth) {
        UpdatePlanChangeRequest payload = new UpdatePlanChangeRequest(
            planId,
            dto.kind(),
            dto.name(),
            dto.price(),
            dto.status(),
            dto.uploadSpeedMbps(),
            dto.downloadSpeedMbps(),
            dto.networkGeneration(),
            dto.dataLimitGb(),
            dto.callCostPerMinute(),
            dto.smsCostPerMessage());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(changeRequestService.submitUpdatePlan(payload, auth.getName()));
        }

    @DeleteMapping("/requests/plans/{planId}")
    @Operation(summary = "Request plan deletion", description = "Queues a DELETE_PLAN request for admin approval.\n\nRoles: OPERATOR")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<ChangeRequestResponse> submitDeletePlan(
            @PathVariable Long planId, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(changeRequestService.submitDeletePlan(new DeletePlanChangeRequest(planId), auth.getName()));
    }

        @PutMapping("/requests/operator/{operatorId}")
        @Operation(summary = "Request operator update", description = "Queues an UPDATE_OPERATOR request for admin approval.\n\nRoles: OPERATOR")
        @PreAuthorize("hasRole('OPERATOR')")
        public ResponseEntity<ChangeRequestResponse> submitUpdateOperator(
            @PathVariable Long operatorId,
            @Valid @RequestBody UpdateOperatorChangeRequest dto,
            Authentication auth) {
        UpdateOperatorChangeRequest payload =
            new UpdateOperatorChangeRequest(operatorId, dto.newName());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(changeRequestService.submitUpdateOperator(payload, auth.getName()));
        }

    @PostMapping("/requests/subscriptions")
    @Operation(summary = "Request subscription creation", description = "Queues a CREATE_SUBSCRIPTION request for admin approval.\n\nRoles: OPERATOR")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<ChangeRequestResponse> submitCreateSubscription(
            @Valid @RequestBody CreateSubscriptionChangeRequest dto, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(changeRequestService.submitCreateSubscription(dto, auth.getName()));
    }

    @GetMapping("/requests/mine")
    @Operation(summary = "Get my change requests", description = "Returns all change requests submitted by the authenticated operator.\n\nRoles: OPERATOR")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<List<ChangeRequestResponse>> getMyRequests(Authentication auth) {
        return ResponseEntity.ok(changeRequestService.getMyRequests(auth.getName()));
    }
}