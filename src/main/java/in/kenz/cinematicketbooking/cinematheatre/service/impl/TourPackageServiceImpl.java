package in.kenz.cinematicketbooking.cinematheatre.service.impl;

import in.kenz.cinematicketbooking.location.entity.Location;
import in.kenz.cinematicketbooking.location.repository.LocationRepository;
import in.kenz.cinematicketbooking.cinematheatre.dto.TourPackageCreateDTO;
import in.kenz.cinematicketbooking.cinematheatre.dto.TourPackageResponseDTO;
import in.kenz.cinematicketbooking.cinematheatre.entity.Cinema;
import in.kenz.cinematicketbooking.cinematheatre.repository.TourPackageRepository;
import in.kenz.cinematicketbooking.cinematheatre.service.TourPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourPackageServiceImpl implements TourPackageService {

    private final TourPackageRepository tourPackageRepository;
    private final LocationRepository locationRepository;

    // 🔥 CREATE → evict location cache
    @Override
    @CacheEvict(
            value = "tourPackagesByLocation",
            key = "#dto.locationId"
    )
    public TourPackageResponseDTO create(TourPackageCreateDTO dto) {

        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Location not found"));

        Cinema cinema = Cinema.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .durationDays(dto.getDurationDays())
                .price(dto.getPrice())
                .location(location)
                .active(true)
                .build();

        tourPackageRepository.save(cinema);
        return toDTO(cinema);
    }

    @Override
    public List<TourPackageResponseDTO> getAll() {
        return tourPackageRepository.findByActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TourPackageResponseDTO getById(UUID id) {
        Cinema cinema = tourPackageRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Tour package not found"));
        return toDTO(cinema);
    }

    // 🔥 READ → cached
    @Override
    @Cacheable(
            value = "tourPackagesByLocation",
            key = "#locationId"
    )
    public List<TourPackageResponseDTO> getByLocation(UUID locationId) {

        System.out.println("🔥 DB HIT for location: " + locationId);

        return tourPackageRepository
                .findByLocation_IdAndActiveTrue(locationId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 🔥 UPDATE → evict cache
    @Override
    @CacheEvict(
            value = "tourPackagesByLocation",
            allEntries = true
    )
    public void updateStatus(UUID id, boolean active) {
        Cinema cinema = tourPackageRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Tour package not found"));
        cinema.setActive(active);
        tourPackageRepository.save(cinema);
    }

    // ---------- Mapper ----------
    private TourPackageResponseDTO toDTO(Cinema cinema) {

        TourPackageResponseDTO dto = new TourPackageResponseDTO();
        dto.setId(cinema.getId());
        dto.setTitle(cinema.getTitle());
        dto.setDescription(cinema.getDescription());
        dto.setDurationDays(cinema.getDurationDays());
        dto.setPrice(cinema.getPrice());
        dto.setLocationId(cinema.getLocation().getId());
        dto.setLocationName(cinema.getLocation().getName());
        dto.setActive(cinema.isActive());

        return dto;
    }
}