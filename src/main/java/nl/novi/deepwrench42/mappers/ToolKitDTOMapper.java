package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.toolKit.ToolKitRequestDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.entities.ToolKitEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ToolKitDTOMapper implements DTOMapper<ToolKitResponseDTO, ToolKitRequestDTO, ToolKitEntity> {

    private final AircraftTypeDTOMapper aircraftTypeDTOMapper;
    private final EngineTypeDTOMapper engineTypeDTOMapper;
    private final ToolDTOMapper toolDTOMapper;

    public ToolKitDTOMapper(ToolDTOMapper toolDTOMapper, AircraftTypeDTOMapper aircraftTypeDTOMapper, EngineTypeDTOMapper engineTypeDTOMapper) {
        this.toolDTOMapper = toolDTOMapper;
        this.aircraftTypeDTOMapper = aircraftTypeDTOMapper;
        this.engineTypeDTOMapper = engineTypeDTOMapper;
    }

    @Override
    public ToolKitResponseDTO mapToDto(ToolKitEntity model) {
        if (model == null) return null;

        ToolKitResponseDTO result = new ToolKitResponseDTO();
        result.setId(model.getId());
        result.setKitContents(toolDTOMapper.mapToDto(model.getKitContents()));
        result.setType(model.getType());
        result.setAtaCode(model.getAtaCode());
        result.setPartNumber(model.getPartNumber());
        result.setSerialNumber(model.getSerialNumber());
        result.setManufacturer(model.getManufacturer());
        result.setApplicableAircraftType(aircraftTypeDTOMapper.mapToDto(model.getApplicableAircraftType()));
        result.setApplicableEngineType(engineTypeDTOMapper.mapToDto(model.getApplicableEngineType()));
        result.setIsCalibrated(model.getIsCalibrated());
        return result;
    }

    @Override
    public List<ToolKitResponseDTO> mapToDto(List<ToolKitEntity> models) {
        if (models == null || models.isEmpty()) return List.of();

        return models.stream()
                .map(this::mapToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public ToolKitEntity mapToEntity(ToolKitRequestDTO dto) {
        if (dto == null) return null;

        ToolKitEntity result = new ToolKitEntity();
        result.setType(dto.getType());
        result.setAtaCode(dto.getAtaCode());
        result.setPartNumber(dto.getPartNumber());
        result.setSerialNumber(dto.getSerialNumber());
        result.setManufacturer(dto.getManufacturer());
        result.setIsCalibrated(dto.getIsCalibrated());
        return result;
    }
}

