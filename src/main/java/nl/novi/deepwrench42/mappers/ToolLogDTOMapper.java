package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.toolLog.ToolLogRequestDTO;
import nl.novi.deepwrench42.dtos.toolLog.ToolLogResponseDTO;
import nl.novi.deepwrench42.entities.ToolLogEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ToolLogDTOMapper implements DTOMapper<ToolLogResponseDTO, ToolLogRequestDTO, ToolLogEntity>{

    private final ToolDTOMapper toolMapper;
    private final ToolKitDTOMapper toolKitMapper;
    private final AircraftDTOMapper aircraftMapper;
    private final UserDTOMapper userMapper;

    public ToolLogDTOMapper(ToolDTOMapper toolMapper, ToolKitDTOMapper toolKitMapper,
                            AircraftDTOMapper aircraftMapper, UserDTOMapper userMapper) {
        this.toolMapper = toolMapper;
        this.toolKitMapper = toolKitMapper;
        this.aircraftMapper = aircraftMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ToolLogResponseDTO mapToDto(ToolLogEntity model) {
        if (model == null) return null;

        var result = new ToolLogResponseDTO();
        result.setId(model.getId());
        result.setTimeStamp(model.getTimeStamp());
        result.setActionType(model.getActionType().name());
        result.setActionBy(userMapper.mapToDto(model.getActionBy()));
        result.setTool(toolMapper.mapToDto(model.getTool()));
        result.setToolKit(toolKitMapper.mapToDto(model.getToolKit()));
        result.setAircraft(aircraftMapper.mapToDto(model.getAircraft()));
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
        model.setActionBy(requestDTO.getActionBy());
        model.setTool(requestDTO.getTool());
        model.setToolKit(requestDTO.getToolKit());
        model.setAircraft(requestDTO.getAircraft());
        model.setComments(requestDTO.getComments());
        return model;
    }
}
