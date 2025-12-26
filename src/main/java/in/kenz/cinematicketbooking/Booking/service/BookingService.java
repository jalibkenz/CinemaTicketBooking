package in.kenz.cinematicketbooking.Booking.service;

import in.kenz.cinematicketbooking.Booking.dto.BookingCreateDTO;
import in.kenz.cinematicketbooking.Booking.dto.BookingResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BookingService {

    BookingResponseDTO createBooking(UUID userId, BookingCreateDTO dto);

    List<BookingResponseDTO> getBookingsByUser(UUID userId);

    void cancelBooking(UUID bookingId);
}