package se.lexicon.subscriptionapi.dto.response;

import java.math.BigDecimal;
import java.util.List;
import se.lexicon.subscriptionapi.domain.constant.NetworkGeneration;
import se.lexicon.subscriptionapi.domain.constant.PlanKind;
import se.lexicon.subscriptionapi.domain.constant.PlanStatus;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.validation.CountryCode;

public record PlanResponse(
        Long id,
        PlanKind kind,
        String name,
        OperatorSummaryResponse operator,
        BigDecimal price,
        PlanStatus status,
        Integer uploadSpeedMbps,
        Integer downloadSpeedMbps,
        NetworkGeneration networkGeneration,
        Integer dataLimitGb,
        BigDecimal callCostPerMinute,
        BigDecimal smsCostPerMessage,
        List<CountryCode> coverage,
        Integer MHz
) {
    public static PlanResponse internet(Plan p, Integer upload, Integer download) {
        return new PlanResponse(
            p.getId(), 
            p.getKind(),
            p.getName(),
            null,
            p.getPrice(),
            p.getStatus(),
            upload,
            download,
            null,
            null,
            null,
            null,
            null,
            null
        );
    }

    public static PlanResponse cellular(Plan p, NetworkGeneration gen, Integer dataLimit, BigDecimal callCost, BigDecimal smsCost) {
        return new PlanResponse(
            p.getId(),
            p.getKind(),
            p.getName(),
            null,
            p.getPrice(),
            p.getStatus(),
            null,
            null,
            gen,
            dataLimit,
            callCost,
            smsCost,
            null,
            null
        );
    }

    public static PlanResponse satellite(Plan p, List<CountryCode> coverage, Integer mhz) {
        return new PlanResponse(
            p.getId(),
            p.getKind(),
            p.getName(),
            null,
            p.getPrice(),
            p.getStatus(),
            null,
            null,
            null,
            null,
            null,
            null,
            coverage,
            mhz
        );
    }
}