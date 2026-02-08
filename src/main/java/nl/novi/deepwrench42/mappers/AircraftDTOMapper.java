package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.aircraft.AircraftRequestDTO;
import nl.novi.deepwrench42.dtos.aircraft.AircraftResponseDTO;
import nl.novi.deepwrench42.entities.AircraftEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AircraftDTOMapper implements DTOMapper<AircraftResponseDTO, AircraftRequestDTO, AircraftEntity> {

    private final AircraftTypeDTOMapper aircraftTypeMapper;
    private final EngineTypeDTOMapper engineTypeMapper;

    public AircraftDTOMapper(AircraftTypeDTOMapper aircraftTypeMapper,
                             EngineTypeDTOMapper engineTypeMapper) {
        this.aircraftTypeMapper = aircraftTypeMapper;
        this.engineTypeMapper = engineTypeMapper;
    }

    @Override
    public AircraftResponseDTO mapToDto(AircraftEntity model) {
        if (model == null) return null;

        AircraftResponseDTO result = new AircraftResponseDTO();
        result.setId(model.getId());
        result.setShipNumber(model.getShipNumber());
        result.setRegistration(model.getRegistration());
        result.setAircraftType(aircraftTypeMapper.mapToDto(model.getAircraftType()));
        result.setEngineType(engineTypeMapper.mapToDto(model.getEngineType()));
        return result;
    }

    @Override
    public List<AircraftResponseDTO> mapToDto(List<AircraftEntity> models) {
        if (models == null || models.isEmpty()) return List.of();

        return models.stream()
                .map(this::mapToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public AircraftEntity mapToEntity(AircraftRequestDTO dto) {
        if (dto == null) return null;

        AircraftEntity result = new AircraftEntity();
        result.setShipNumber(dto.getShipNumber());
        result.setRegistration(dto.getRegistration());
        return result;
    }
}