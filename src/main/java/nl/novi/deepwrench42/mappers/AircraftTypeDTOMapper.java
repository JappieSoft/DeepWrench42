package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeRequestDTO;
import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.entities.AircraftTypeEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AircraftTypeDTOMapper implements DTOMapper<AircraftTypeResponseDTO, AircraftTypeRequestDTO, AircraftTypeEntity>{

    @Override
    public AircraftTypeResponseDTO mapToDto(AircraftTypeEntity model) {
        if (model == null) return null;

        var result = new AircraftTypeResponseDTO();
        result.setId(model.getId());
        result.setManufacturer(model.getManufacturer());
        result.setMainType(model.getMainType());
        result.setSubType(model.getSubType());
        return result;
    }

    @Override
    public List<AircraftTypeResponseDTO> mapToDto(List<AircraftTypeEntity> models) {
        if (models == null || models.isEmpty()) return List.of();

        return models.stream()
                .map(this::mapToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public AircraftTypeEntity mapToEntity(AircraftTypeRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        var model = new AircraftTypeEntity();
        model.setManufacturer(requestDTO.getManufacturer());
        model.setMainType(requestDTO.getMainType());
        model.setSubType(requestDTO.getSubType());
        return model;
    }
}
