package in.kenz.cinematicketbooking.cinematheatre.repository;

import in.kenz.cinematicketbooking.cinematheatre.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TourPackageRepository extends JpaRepository<Cinema, UUID> {

    List<Cinema> findByActiveTrue();

    List<Cinema> findByLocation_IdAndActiveTrue(UUID locationId);
}