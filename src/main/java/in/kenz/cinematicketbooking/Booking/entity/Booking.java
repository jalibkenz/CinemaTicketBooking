package in.kenz.cinematicketbooking.Booking.entity;

import in.kenz.cinematicketbooking.Booking.enums.BookingStatus;
import in.kenz.cinematicketbooking.common.entity.BaseEntity;
import in.kenz.cinematicketbooking.cinematheatre.entity.Cinema;
import in.kenz.cinematicketbooking.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_package_id", nullable = false)
    private Cinema cinema;

    @Column(nullable = false)
    private LocalDate travelDate;

    @Column(nullable = false)
    private int travelers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus status;

    @Column(nullable = false)
    private boolean active = true;
}