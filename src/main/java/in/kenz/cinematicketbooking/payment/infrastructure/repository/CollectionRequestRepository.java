package in.kenz.cinematicketbooking.payment.infrastructure.repository;

import in.kenz.cinematicketbooking.Booking.entity.Booking;
import in.kenz.cinematicketbooking.payment.domain.entity.CollectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CollectionRequestRepository extends JpaRepository<CollectionRequest, UUID> {

    /* ---------------------------------
       Booking-level lookup
       --------------------------------- */

    // Each booking can have at most one active CollectionRequest
    Optional<CollectionRequest> findByBooking(Booking booking);

    /* ---------------------------------
       State-based lookup
       --------------------------------- */

    // Useful if you later allow multiple collection requests per booking
    Optional<CollectionRequest> findByBookingAndActiveTrue(Booking booking);
}