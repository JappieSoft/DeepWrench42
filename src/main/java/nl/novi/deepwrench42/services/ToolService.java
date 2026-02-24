package nl.novi.deepwrench42.services;

import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.tool.ToolRequestDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.ToolDTOMapper;
import nl.novi.deepwrench42.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToolService {
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;
    private final AircraftTypeRepository aircraftTypeRepository;
    private final EngineTypeRepository engineTypeRepository;
    private final InspectionRepository inspectionRepository;
    private final ToolDTOMapper toolDTOMapper;

    public ToolService(ToolRepository toolRepository, ToolKitRepository toolKitRepository, AircraftTypeRepository aircraftTypeRepository, EngineTypeRepository engineTypeRepository, InspectionRepository inspectionRepository, ToolDTOMapper toolDTOMapper) {
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        this.aircraftTypeRepository = aircraftTypeRepository;
        this.engineTypeRepository = engineTypeRepository;
        this.inspectionRepository = inspectionRepository;
        this.toolDTOMapper = toolDTOMapper;
    }

    @Transactional
    public List<ToolResponseDTO> findAllTools() {
        return toolDTOMapper.mapToDto(toolRepository.findAll());
    }

    @Transactional
    public ToolResponseDTO findToolById(Long id) {
        ToolEntity toolEntity = getToolEntity(id);
        return toolDTOMapper.mapToDto(toolEntity);
    }

    @Transactional
    public ToolResponseDTO createTool(ToolRequestDTO model) {
        ToolEntity toolEntity = toolDTOMapper.mapToEntity(model);
        mapIdsToEntities(toolEntity, model);
        toolEntity = toolRepository.save(toolEntity);
        return toolDTOMapper.mapToDto(toolEntity);
    }

    @Transactional
    public ToolResponseDTO updateTool(Long id, ToolRequestDTO requestDto) {
        ToolEntity existingEntity = getToolEntity(id);
        mapIdsToEntities(existingEntity, requestDto);
        existingEntity.setToolType(requestDto.getToolType());
        existingEntity.setAtaCode(requestDto.getAtaCode());
        existingEntity.setPartNumber(requestDto.getPartNumber());
        existingEntity.setSerialNumber(requestDto.getSerialNumber());
        existingEntity.setManufacturer(requestDto.getManufacturer());
        // aircraft types
        // engine types
        existingEntity.setIsCalibrated(requestDto.getIsCalibrated());
        // inspection
        // tool kit


        existingEntity = toolRepository.save(existingEntity);
        return toolDTOMapper.mapToDto(existingEntity);
    }

    @Transactional
    public void deleteTool(Long id) {
        ToolEntity tool = getToolEntity(id);
        toolRepository.delete(tool);
    }

    //Tool to toolKit functions, old function might go away:
    @Transactional
    public ToolResponseDTO addToToolKit(Long toolId, String kitItemId) {
        ToolEntity tool = toolRepository.findById(toolId).orElseThrow();
        tool.setStorageLocation(null);
        return toolDTOMapper.mapToDto(toolRepository.save(tool));
    }

    @Transactional
    public ToolResponseDTO removeFromToolKit(Long toolId) {
        ToolEntity tool = toolRepository.findById(toolId).orElseThrow();
        return toolDTOMapper.mapToDto(toolRepository.save(tool));
    }


    // Service Helpers
    private void mapIdsToEntities(ToolEntity entity, ToolRequestDTO requestDto) {
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
        if (requestDto.getInspectionId() != null) {
            entity.setInspection(inspectionRepository.getReferenceById(requestDto.getInspectionId()));
        }
        if (requestDto.getToolKitId() != null) {
            entity.setToolKit(toolKitRepository.getReferenceById(requestDto.getToolKitId()));
        }
    }

    private ToolEntity getToolEntity(Long id) {
        return toolRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Tool " + id + " not found"));
    }
}