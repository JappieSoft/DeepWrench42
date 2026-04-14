package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.toolKit.ToolKitRequestDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.entities.ToolKitEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ToolKitDTOMapper extends EquipmentDTOMapper implements DTOMapper<ToolKitResponseDTO, ToolKitRequestDTO, ToolKitEntity> {

    private final AircraftTypeDTOMapper aircraftTypeDTOMapper;
    private final EngineTypeDTOMapper engineTypeDTOMapper;
    private final InspectionDTOMapper inspectionDTOMapper;
    private final ToolDTOMapper toolDTOMapper;

    public ToolKitDTOMapper(
            @Lazy AircraftDTOMapper aircraftDTOMapper,
            @Lazy AircraftTypeDTOMapper aircraftTypeDTOMapper,
            @Lazy EngineTypeDTOMapper engineTypeDTOMapper,
            @Lazy StorageLocationDTOMapper storageLocationDTOMapper,
            @Lazy UserDTOMapper userDTOMapper,
            @Lazy InspectionDTOMapper inspectionDTOMapper,
            @Lazy ToolDTOMapper toolDTOMapper
    ) {

        super(storageLocationDTOMapper, userDTOMapper);
        this.aircraftTypeDTOMapper = aircraftTypeDTOMapper;
        this.engineTypeDTOMapper = engineTypeDTOMapper;
        this.inspectionDTOMapper = inspectionDTOMapper;
        this.toolDTOMapper = toolDTOMapper;
    }

    @Override
    public ToolKitResponseDTO mapToDto(ToolKitEntity model) {
        if (model == null) return null;

        ToolKitResponseDTO result = new ToolKitResponseDTO();
        equipmentMapToDto(model, result);

        result.setKitContents(
                new HashSet<>(
                        toolDTOMapper.mapToDto(
                                model.getKitContents() != null ?
                                        new ArrayList<>(model.getKitContents()) :
                                        List.of())));
        result.setToolKitType(model.getToolKitType());
        result.setAtaCode(model.getAtaCode());
        result.setPartNumber(model.getPartNumber());
        result.setSerialNumber(model.getSerialNumber());
        result.setManufacturer(model.getManufacturer());
        result.setApplicableAircraftTypes(
                new HashSet<>(
                        aircraftTypeDTOMapper.mapToDto(
                                model.getApplicableAircraftTypes() != null ?
                                        new ArrayList<>(model.getApplicableAircraftTypes()) :
                                        List.of())));
        result.setApplicableEngineTypes(
                new HashSet<>(
                        engineTypeDTOMapper.mapToDto(
                                model.getApplicableEngineTypes() != null ?
                                        new ArrayList<>(model.getApplicableEngineTypes()) :
                                        List.of())));
        result.setIsCalibrated(model.getIsCalibrated());
        result.setInspection(inspectionDTOMapper.mapToDto(model.getInspection()));
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
    public ToolKitEntity mapToEntity(ToolKitRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        ToolKitEntity result = new ToolKitEntity();
        mapToEquipmentEntity(requestDTO, result);

        result.setToolKitType(requestDTO.getToolKitType());
        result.setAtaCode(requestDTO.getAtaCode());
        result.setPartNumber(requestDTO.getPartNumber());
        result.setSerialNumber(requestDTO.getSerialNumber());
        result.setManufacturer(requestDTO.getManufacturer());
        result.setIsCalibrated(requestDTO.getIsCalibrated());
        return result;
    }
}

