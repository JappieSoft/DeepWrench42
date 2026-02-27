package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.dtos.tool.ToolRequestDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.entities.EquipmentStatus;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class ToolDTOMapper implements DTOMapper<ToolResponseDTO, ToolRequestDTO, ToolEntity> {

    private final AircraftTypeDTOMapper aircraftTypeDTOMapper;
    private final EngineTypeDTOMapper engineTypeDTOMapper;
    private final StorageLocationDTOMapper storageLocationDTOMapper;
    private final InspectionDTOMapper inspectionDTOMapper;

    public ToolDTOMapper(@Lazy AircraftTypeDTOMapper aircraftTypeDTOMapper, @Lazy EngineTypeDTOMapper engineTypeDTOMapper, @Lazy StorageLocationDTOMapper storageLocationDTOMapper, @Lazy InspectionDTOMapper inspectionDTOMapper) {
        this.aircraftTypeDTOMapper = aircraftTypeDTOMapper;
        this.engineTypeDTOMapper = engineTypeDTOMapper;
        this.storageLocationDTOMapper = storageLocationDTOMapper;
        this.inspectionDTOMapper = inspectionDTOMapper;
    }

    @Override
    public ToolResponseDTO mapToDto(ToolEntity model) {
        if (model == null) return null;

        ToolResponseDTO result = new ToolResponseDTO();
        result.setId(model.getId());
        result.setEquipmentType(model.getEquipmentType());
        result.setItemId(model.getItemId());
        result.setName(model.getName());
        result.setPicture(model.getPicture());
        result.setStorageLocation(storageLocationDTOMapper.mapToDto(model.getStorageLocation()));
        result.setStatus(model.getStatus());
        result.setCheckedOutDate(model.getCheckedOutDate());
        result.setHasInspection(model.getHasInspection());
        result.setComments(model.getComments());
        result.setToolType(model.getToolType());
        result.setAtaCode(model.getAtaCode());
        result.setPartNumber(model.getPartNumber());
        result.setSerialNumber(model.getSerialNumber());
        result.setManufacturer(model.getManufacturer());
        result.setApplicableAircraftTypes(
                new HashSet<>(
                        aircraftTypeDTOMapper.mapToDto(
                                model.getApplicableAircraftTypes() != null
                                        ? new ArrayList<>(model.getApplicableAircraftTypes())
                                        : List.of())));
        result.setApplicableEngineTypes(
                new HashSet<>(
                        engineTypeDTOMapper.mapToDto(
                                model.getApplicableEngineTypes() != null
                                        ? new ArrayList<>(model.getApplicableEngineTypes())
                                        : List.of())));
        result.setIsCalibrated(model.getIsCalibrated());
        result.setInspection(inspectionDTOMapper.mapToDto(model.getInspection()));
        result.setToolKit(model.getToolKit() != null ? model.getToolKit().getId() : null);
        if (model.getToolKit() != null) {
            result.setToolKitItemId(model.getToolKit().getItemId());
        } else {
            result.setToolKitItemId(null);
        }

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
    public ToolEntity mapToEntity(ToolRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        ToolEntity result = new ToolEntity();
        result.setEquipmentType(requestDTO.getEquipmentType());
        result.setItemId(requestDTO.getItemId());
        result.setName(requestDTO.getName());
        result.setPicture(requestDTO.getPicture());
        result.setStatus(EquipmentStatus.valueOf(requestDTO.getStatus()));
        result.setCheckedOutDate(requestDTO.getCheckedOutDate());
        result.setHasInspection(requestDTO.getHasInspection());
        result.setComments(requestDTO.getComments());
        result.setToolType(requestDTO.getToolType());
        result.setAtaCode(requestDTO.getAtaCode());
        result.setPartNumber(requestDTO.getPartNumber());
        result.setSerialNumber(requestDTO.getSerialNumber());
        result.setManufacturer(requestDTO.getManufacturer());
        result.setIsCalibrated(requestDTO.getIsCalibrated());

        return result;
    }
}
