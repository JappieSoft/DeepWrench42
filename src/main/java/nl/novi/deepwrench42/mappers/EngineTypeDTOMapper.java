package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.engineType.EngineTypeRequestDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;
import nl.novi.deepwrench42.entities.EngineTypeEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EngineTypeDTOMapper  implements DTOMapper<EngineTypeResponseDTO, EngineTypeRequestDTO, EngineTypeEntity>{

    @Override
    public EngineTypeResponseDTO mapToDto(EngineTypeEntity model) {
        if (model == null) return null;

        var result = new EngineTypeResponseDTO();
        result.setId(model.getId());
        result.setManufacturer(model.getManufacturer());
        result.setMainType(model.getMainType());
        result.setSubType(model.getSubType());
        return result;
    }

    @Override
    public List<EngineTypeResponseDTO> mapToDto(List<EngineTypeEntity> models) {
        if (models == null || models.isEmpty()) return List.of();

        return models.stream()
                .map(this::mapToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public EngineTypeEntity mapToEntity(EngineTypeRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        var model = new EngineTypeEntity();
        model.setManufacturer(requestDTO.getManufacturer());
        model.setMainType(requestDTO.getMainType());
        model.setSubType(requestDTO.getSubType());
        return model;
    }
}
