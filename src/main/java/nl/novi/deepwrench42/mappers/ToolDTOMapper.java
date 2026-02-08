package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.dtos.tool.ToolRequestDTO;
import nl.novi.deepwrench42.entities.ToolEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class ToolDTOMapper implements DTOMapper<ToolResponseDTO, ToolRequestDTO, ToolEntity> {

    private final AircraftTypeDTOMapper aircraftTypeDTOMapper;
    private final EngineTypeDTOMapper engineTypeDTOMapper;
    private final ToolKitDTOMapper toolKitDTOMapper;

    public ToolDTOMapper(AircraftTypeDTOMapper aircraftTypeDTOMapper, EngineTypeDTOMapper engineTypeDTOMapper, ToolKitDTOMapper toolKitDTOMapper) {
        this.aircraftTypeDTOMapper = aircraftTypeDTOMapper;
        this.engineTypeDTOMapper = engineTypeDTOMapper;
        this.toolKitDTOMapper = toolKitDTOMapper;
    }

    @Override
    public ToolResponseDTO mapToDto(ToolEntity model) {
        if (model == null) return null;

        ToolResponseDTO result = new ToolResponseDTO();
        result.setId(model.getId());
        result.setType(model.getType());
        result.setAtaCode(model.getAtaCode());
        result.setPartNumber(model.getPartNumber());
        result.setSerialNumber(model.getSerialNumber());
        result.setManufacturer(model.getManufacturer());
        result.setApplicableAircraftType(aircraftTypeDTOMapper.mapToDto(model.getApplicableAircraftType()));
        result.setApplicableEngineType(engineTypeDTOMapper.mapToDto(model.getApplicableEngineType()));
        result.setIsCalibrated(model.getIsCalibrated());
        result.setToolKit(toolKitDTOMapper.mapToDto(model.getToolKit()));
        return result;
    }

    @Override
    public List<ToolResponseDTO> mapToDto(List<ToolEntity> models) {
        if (models == null || models.isEmpty()) return List.of();

        return models.stream()
                .map(this::mapToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public ToolEntity mapToEntity(ToolRequestDTO dto) {
        if (dto == null) return null;

        ToolEntity result = new ToolEntity();
        result.setType(dto.getType());
        result.setAtaCode(dto.getAtaCode());
        result.setPartNumber(dto.getPartNumber());
        result.setSerialNumber(dto.getSerialNumber());
        result.setManufacturer(dto.getManufacturer());
        result.setIsCalibrated(dto.getIsCalibrated());
        return result;
    }
}