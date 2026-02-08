package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.inspection.InspectionRequestDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionResponseDTO;
import nl.novi.deepwrench42.entities.InspectionEntity;
import nl.novi.deepwrench42.entities.InspectionType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class InspectionDTOMapper  implements DTOMapper<InspectionResponseDTO, InspectionRequestDTO, InspectionEntity>{

    private final ToolDTOMapper toolDTOMapper;

    public InspectionDTOMapper(ToolDTOMapper toolDTOMapper) {
        this.toolDTOMapper = toolDTOMapper;
    }

    @Override
    public InspectionResponseDTO mapToDto(InspectionEntity model) {
        if (model == null) return null;

        var result = new InspectionResponseDTO();
        result.setId(model.getId());
        result.setInspectionDate(model.getInspectionDate());
        result.setInspectionType(model.getInspectionType().name());
        result.setNextDueDate(model.getNextDueDate());
        result.setInspectionInterval(model.getInspectionInterval());
        result.setTool(toolDTOMapper.mapToDto(model.getTool()));
        return result;
    }

    @Override
    public List<InspectionResponseDTO> mapToDto(List<InspectionEntity> models) {
        if (models == null || models.isEmpty()) return List.of();

        return models.stream()
                .map(this::mapToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public InspectionEntity mapToEntity(InspectionRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        var model = new InspectionEntity();
        model.setInspectionDate(requestDTO.getInspectionDate());
        model.setInspectionType(InspectionType.valueOf(requestDTO.getInspectionType()));
        model.setNextDueDate(requestDTO.getNextDueDate());
        model.setInspectionInterval(requestDTO.getInspectionInterval());
        return model;
    }
}
