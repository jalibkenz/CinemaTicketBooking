package in.kenz.cinematicketbooking.location.service;

import in.kenz.cinematicketbooking.location.dto.LocationDTO;

import java.util.List;
import java.util.UUID;

public interface LocationService {

    LocationDTO create(LocationDTO dto);

    List<LocationDTO> getByType(String type);

    List<LocationDTO> getByParent(UUID parentId);

    LocationDTO getById(UUID id);
}