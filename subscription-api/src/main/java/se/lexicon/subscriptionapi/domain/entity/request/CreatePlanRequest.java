package se.lexicon.subscriptionapi.domain.entity.request;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import se.lexicon.subscriptionapi.domain.constant.NetworkGeneration;
import se.lexicon.subscriptionapi.domain.constant.PlanKind;
import se.lexicon.subscriptionapi.domain.constant.PlanStatus;
import se.lexicon.subscriptionapi.domain.entity.ChangeRequest;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("CREATE")
public class CreatePlanRequest extends ChangeRequest {

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_kind")
    private PlanKind planKind;

    private String planName;

    private BigDecimal planPrice;

    @Enumerated(EnumType.STRING)
    private PlanStatus planStatus;

    private Integer uploadSpeedMbps;
    private Integer downloadSpeedMbps;

    @Enumerated(EnumType.STRING)
    private NetworkGeneration networkGeneration;

    private Integer dataLimitGb;
    private BigDecimal callCostPerMinute;
    private BigDecimal smsCostPerMessage;
}
