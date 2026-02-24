package nl.novi.deepwrench42.services;

import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitRequestDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.entities.ToolKitEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.ToolKitDTOMapper;
import nl.novi.deepwrench42.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToolKitService {
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;
    private final AircraftTypeRepository aircraftTypeRepository;
    private final EngineTypeRepository engineTypeRepository;
    private final InspectionRepository inspectionRepository;
    private final ToolKitDTOMapper toolKitDTOMapper;

    public ToolKitService(ToolRepository toolRepository, ToolKitRepository toolKitRepository, AircraftTypeRepository aircraftTypeRepository, EngineTypeRepository engineTypeRepository, InspectionRepository inspectionRepository, ToolKitDTOMapper toolKitDTOMapper) {
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        this.aircraftTypeRepository = aircraftTypeRepository;
        this.engineTypeRepository = engineTypeRepository;
        this.inspectionRepository = inspectionRepository;
        this.toolKitDTOMapper = toolKitDTOMapper;
    }

    @Transactional
    public List<ToolKitResponseDTO> findAllToolKits() {
        return toolKitDTOMapper.mapToDto(toolKitRepository.findAll());
    }

    @Transactional
    public ToolKitResponseDTO findToolKitById(Long id) {
        ToolKitEntity toolKitEntity = getToolKitEntity(id);
        return toolKitDTOMapper.mapToDto(toolKitEntity);
    }

    @Transactional
    public ToolKitResponseDTO createToolKit(ToolKitRequestDTO model) {
        ToolKitEntity toolKitEntity = toolKitDTOMapper.mapToEntity(model);
        mapIdsToEntities(toolKitEntity, model);
        toolKitEntity = toolKitRepository.save(toolKitEntity);
        return toolKitDTOMapper.mapToDto(toolKitEntity);
    }

    @Transactional
    public ToolKitResponseDTO updateToolKit(Long id, ToolKitRequestDTO requestDto) {
        ToolKitEntity existingEntity = getToolKitEntity(id);
        mapIdsToEntities(existingEntity, requestDto);
        existingEntity.setToolKitType(requestDto.getToolKitType());
        existingEntity.setAtaCode(requestDto.getAtaCode());
        existingEntity.setPartNumber(requestDto.getPartNumber());
        existingEntity.setSerialNumber(requestDto.getSerialNumber());
        existingEntity.setManufacturer(requestDto.getManufacturer());
        existingEntity.setIsCalibrated(requestDto.getIsCalibrated());

        existingEntity = toolKitRepository.save(existingEntity);
        return toolKitDTOMapper.mapToDto(existingEntity);
    }

    @Transactional
    public void deleteToolKit(Long id) {
        ToolKitEntity toolKit = getToolKitEntity(id);
        toolKitRepository.delete(toolKit);
    }


    private void mapIdsToEntities(ToolKitEntity entity, ToolKitRequestDTO requestDto) {
        if (requestDto.getApplicableAircraftTypeIds() != null && !requestDto.getApplicableAircraftTypeIds().isEmpty()) {
            entity.setApplicableAircraftTypes(
                    requestDto.getApplicableAircraftTypeIds().stream()
                            .map(aircraftTypeRepository::getReferenceById)
                            .collect(Collectors.toSet())
            );
        }
        if (requestDto.getApplicableEngineTypeIds() != null && !requestDto.getApplicableEngineTypeIds().isEmpty()) {
            entity.setApplicableEngineTypes(
                    requestDto.getApplicableEngineTypeIds().stream()
                            .map(engineTypeRepository::getReferenceById)
                            .collect(Collectors.toSet())
            );
        }
        if (requestDto.getInspectionId() != null) {  // FIXED
            entity.setInspection(inspectionRepository.getReferenceById(requestDto.getInspectionId()));
        }
        if (requestDto.getKitContentsIds() != null && !requestDto.getKitContentsIds().isEmpty()) {
            entity.setKitContents(
                    requestDto.getKitContentsIds().stream()
                            .map(toolRepository::getReferenceById)
                            .collect(Collectors.toSet())
            );
        }
    }

    private ToolKitEntity getToolKitEntity(Long id) {
        return toolKitRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Tool Kit " + id + " not found"));
    }
}
