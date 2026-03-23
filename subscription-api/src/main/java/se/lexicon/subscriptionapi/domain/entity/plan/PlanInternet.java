package se.lexicon.subscriptionapi.domain.entity.plan;

import jakarta.persistence.*;
import lombok.*;
import se.lexicon.subscriptionapi.domain.entity.Plan;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("INTERNET")
public class PlanInternet extends Plan {
    @Embedded
    private Speed speed = new Speed();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Speed {
        @Column(name = "upload_mbps")
        private Integer upload;

        @Column(name = "download_mbps")
        private Integer download;
    }
}
