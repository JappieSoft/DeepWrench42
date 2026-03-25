package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.inspection.InspectionRequestDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionResponseDTO;
import nl.novi.deepwrench42.entities.InspectionEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class InspectionDTOMapper  implements DTOMapper<InspectionResponseDTO, InspectionRequestDTO, InspectionEntity>{

   public InspectionDTOMapper() { }

    @Override
    public InspectionResponseDTO mapToDto(InspectionEntity model) {
        if (model == null) return null;

        var result = new InspectionResponseDTO();
        result.setId(model.getId());
        result.setInspectionDate(model.getInspectionDate());
        result.setInspectionType(model.getInspectionType());
        result.setInspectionPassed(model.getInspectionPassed());
        result.setComments(model.getComments());
        result.setNextDueDate(model.getNextDueDate());
        result.setInspectionInterval(model.getInspectionInterval());
        result.setToolId(model.getTool() != null ? model.getTool().getId() : null);
        result.setToolKitId(model.getToolKit() != null ? model.getToolKit().getId() : null);

        if (model.getTool() != null) {
            result.setEquipmentItemId(model.getTool().getItemId());
        } else if (model.getToolKit() != null) {
            result.setEquipmentItemId(model.getToolKit().getItemId());
        } else {
            result.setEquipmentItemId(null);
        }
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
        model.setInspectionType(requestDTO.getInspectionType());
        model.setNextDueDate(requestDTO.getNextDueDate());
        model.setInspectionInterval(requestDTO.getInspectionInterval());
        return model;
    }
}
