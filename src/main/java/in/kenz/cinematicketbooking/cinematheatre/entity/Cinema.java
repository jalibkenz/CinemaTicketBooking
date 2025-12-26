package in.kenz.cinematicketbooking.cinematheatre.entity;

import in.kenz.cinematicketbooking.common.entity.BaseEntity;
import in.kenz.cinematicketbooking.location.entity.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tour_packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cinema extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Positive
    @Column(nullable = false)
    private int durationDays;

    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(nullable = false)
    private boolean active = true;
}