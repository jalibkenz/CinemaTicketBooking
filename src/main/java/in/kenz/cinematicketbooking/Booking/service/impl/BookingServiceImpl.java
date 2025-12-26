package in.kenz.cinematicketbooking.Booking.service.impl;

import in.kenz.cinematicketbooking.Booking.dto.BookingCreateDTO;
import in.kenz.cinematicketbooking.Booking.dto.BookingResponseDTO;
import in.kenz.cinematicketbooking.Booking.entity.Booking;
import in.kenz.cinematicketbooking.Booking.enums.BookingStatus;
import in.kenz.cinematicketbooking.Booking.repository.BookingRepository;
import in.kenz.cinematicketbooking.Booking.service.BookingService;
import in.kenz.cinematicketbooking.cinematheatre.entity.Cinema;
import in.kenz.cinematicketbooking.cinematheatre.repository.TourPackageRepository;
import in.kenz.cinematicketbooking.user.entity.User;
import in.kenz.cinematicketbooking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final TourPackageRepository tourPackageRepository;
    private final UserRepository userRepository;

    @Override
    public BookingResponseDTO createBooking(UUID userId, BookingCreateDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Cinema cinema = tourPackageRepository.findById(dto.getTourPackageId())
                .orElseThrow(() -> new IllegalArgumentException("Tour package not found"));

        Booking booking = Booking.builder()
                .user(user)
                .cinema(cinema)
                .travelDate(dto.getTravelDate())
                .travelers(dto.getTravelers())
                .status(BookingStatus.CREATED)
                .active(true)
                .build();

        bookingRepository.save(booking);
        return toDTO(booking);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByUser(UUID userId) {

        return bookingRepository.findByUser_Id(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(UUID bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    private BookingResponseDTO toDTO(Booking booking) {

        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getId());
        dto.setTourPackageId(booking.getCinema().getId());
        dto.setTourTitle(booking.getCinema().getTitle());
        dto.setTravelDate(booking.getTravelDate());
        dto.setTravelers(booking.getTravelers());
        dto.setStatus(booking.getStatus().name());

        return dto;
    }
}