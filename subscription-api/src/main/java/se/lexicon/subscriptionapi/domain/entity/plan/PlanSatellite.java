package se.lexicon.subscriptionapi.domain.entity.plan;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.validation.CountryCode;
import se.lexicon.subscriptionapi.validation.CountryCodeConverter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("SATELLITE")
public class PlanSatellite extends Plan {
    @ElementCollection
    @CollectionTable(name = "satellite_coverage", joinColumns = @JoinColumn(name = "plan_id"))
    @Column(name = "country_code")
    @Convert(converter = CountryCodeConverter.class)
    private List<CountryCode> coverage;

    @Column(name = "frequency_band")
    private Integer MHz;
}
