package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.toolLog.ToolLogRequestDTO;
import nl.novi.deepwrench42.dtos.toolLog.ToolLogResponseDTO;
import nl.novi.deepwrench42.entities.ToolLogEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ToolLogDTOMapper implements DTOMapper<ToolLogResponseDTO, ToolLogRequestDTO, ToolLogEntity> {

    @Override
    public ToolLogResponseDTO mapToDto(ToolLogEntity model) {
        if (model == null) return null;

        var result = new ToolLogResponseDTO();
        result.setId(model.getId());
        result.setTimeStamp(model.getTimeStamp());
        result.setActionType(String.valueOf(model.getActionType()));
        result.setActionResult(String.valueOf(model.getActionResult()));
        result.setActionBy(model.getActionBy());
        result.setItemNumber(model.getItemNumber());
        result.setItemType(model.getItemType());
        result.setItemName(model.getItemName());
        result.setAtaCode(model.getAtaCode());
        result.setPartNumber(model.getPartNumber());
        result.setSerialNumber(model.getSerialNumber());
        result.setManufacturer(model.getManufacturer());
        result.setAircraftNumber(model.getAircraftNumber());
        result.setComments(model.getComments());
        return result;
    }

    @Override
    public List<ToolLogResponseDTO> mapToDto(List<ToolLogEntity> models) {
        if (models == null || models.isEmpty()) return List.of();

        return models.stream()
                .map(this::mapToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public ToolLogEntity mapToEntity(ToolLogRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        var model = new ToolLogEntity();
        model.setTimeStamp(requestDTO.getTimeStamp());
        model.setActionType(requestDTO.getActionType());
        model.setActionResult(requestDTO.getActionResult());
        model.setActionBy(requestDTO.getActionBy());
        model.setItemNumber(requestDTO.getItemNumber());
        model.setItemType(requestDTO.getItemType());
        model.setItemName(requestDTO.getItemName());
        model.setAtaCode(requestDTO.getAtaCode());
        model.setPartNumber(requestDTO.getPartNumber());
        model.setSerialNumber(requestDTO.getSerialNumber());
        model.setManufacturer(requestDTO.getManufacturer());
        model.setAircraftNumber(requestDTO.getAircraftNumber());
        model.setComments(requestDTO.getComments());

        return model;
    }

}
