package nl.novi.deepwrench42.services;

import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.tool.ToolRequestDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.entities.*;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.ToolDTOMapper;
import nl.novi.deepwrench42.repository.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ToolService {
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;
    private final AircraftTypeRepository aircraftTypeRepository;
    private final EngineTypeRepository engineTypeRepository;
    private final InspectionRepository inspectionRepository;
    private final StorageLocationRepository storageLocationRepository;
    private final UserRepository userRepository;
    private final ToolDTOMapper toolDTOMapper;

    public ToolService(ToolRepository toolRepository, ToolKitRepository toolKitRepository, AircraftTypeRepository aircraftTypeRepository, EngineTypeRepository engineTypeRepository, InspectionRepository inspectionRepository, StorageLocationRepository storageLocationRepository, UserRepository userRepository, ToolDTOMapper toolDTOMapper) {
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        this.aircraftTypeRepository = aircraftTypeRepository;
        this.engineTypeRepository = engineTypeRepository;
        this.inspectionRepository = inspectionRepository;
        this.storageLocationRepository = storageLocationRepository;
        this.userRepository = userRepository;
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

        toolEntity.setEquipmentType(model.getEquipmentType());
        toolEntity.setItemId(model.getItemId());
        toolEntity.setName(model.getName());
        toolEntity.setPicture(model.getPicture());
        if (model.getStorageLocation() != null) {
            Long storageLocationId = model.getStorageLocation();

            if (toolRepository.existsByStorageLocationId(storageLocationId)) {
                throw new IllegalArgumentException("Storage location ID " + storageLocationId + " already assigned to a tool");
            }
            if (toolKitRepository.existsByStorageLocationId(storageLocationId)) {
                throw new IllegalArgumentException("Storage location ID " + storageLocationId + " already assigned to a tool kit");
            }

            StorageLocationEntity storageLocation = storageLocationRepository
                    .findById(storageLocationId)
                    .orElseThrow(() -> new RecordNotFoundException("Storage location not found"));
            toolEntity.setStorageLocation(storageLocation);
        }

        if (model.getStatus() != null) {
            toolEntity.setStatus(EquipmentStatus.valueOf(model.getStatus().toUpperCase()));
        }
        if (model.getCheckedOutBy() != null) {
            UserEntity checkedOutBy = userRepository
                    .findById(model.getCheckedOutBy())
                    .orElseThrow(() -> new RecordNotFoundException("User not found"));
            toolEntity.setCheckedOutBy(checkedOutBy);
        }
        toolEntity.setCheckedOutDate(model.getCheckedOutDate());
        toolEntity.setHasInspection(model.getHasInspection());
        toolEntity.setComments(model.getComments());

        //Tool-specific
        toolEntity.setToolType(model.getToolType());
        toolEntity.setAtaCode(model.getAtaCode());
        toolEntity.setPartNumber(model.getPartNumber());
        toolEntity.setSerialNumber(model.getSerialNumber());
        toolEntity.setManufacturer(model.getManufacturer());
        Set<Long> aircraftTypeIds = model.getApplicableAircraftTypeIds();
        List<AircraftTypeEntity> aircraftTypesList = aircraftTypeRepository.findAllById(aircraftTypeIds);
        if (aircraftTypesList.isEmpty()) {
            throw new RecordNotFoundException("No Aircraft Types found: " + aircraftTypeIds);
        }
        toolEntity.setApplicableAircraftTypes(new HashSet<>(aircraftTypesList));

        Set<Long> engineTypeIds = model.getApplicableEngineTypeIds();
        List<EngineTypeEntity> engineTypesList = engineTypeRepository.findAllById(engineTypeIds);
        if (engineTypesList.isEmpty()) {
            throw new RecordNotFoundException("No Engine Types found: " + engineTypeIds);
        }
        toolEntity.setApplicableEngineTypes(new HashSet<>(engineTypesList));
        toolEntity.setIsCalibrated(model.getIsCalibrated());
        if (model.getInspectionId() != null) {
            InspectionEntity inspection = inspectionRepository
                    .findById(model.getInspectionId())
                    .orElseThrow(() -> new RecordNotFoundException("Inspection not found"));
            toolEntity.setInspection(inspection);
        }
        if (model.getToolKitId() != null) {
            ToolKitEntity toolKit = toolKitRepository
                    .findById(model.getToolKitId())
                    .orElseThrow(() -> new RecordNotFoundException("Tool kit not found"));
            toolEntity.setToolKit(toolKit);
        }

        toolEntity = toolRepository.save(toolEntity);
        return toolDTOMapper.mapToDto(toolEntity);
    }

    @Transactional
    public ToolResponseDTO updateTool(Long id, ToolRequestDTO requestDto) {
        ToolEntity existingEntity = toolRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Tool " + id + " not found"));

        existingEntity.setEquipmentType(requestDto.getEquipmentType());
        existingEntity.setItemId(requestDto.getItemId());
        existingEntity.setName(requestDto.getName());
        existingEntity.setPicture(requestDto.getPicture());
        if (requestDto.getStorageLocation() != null) {
            Long storageLocationId = requestDto.getStorageLocation();
            if (!storageLocationId.equals(existingEntity.getStorageLocation() != null ? existingEntity.getStorageLocation().getId() : null)) {
                if (toolRepository.existsByStorageLocationId(storageLocationId)) {
                    throw new IllegalArgumentException("Storage location ID " + storageLocationId + " is already assigned to another tool");
                }
                if (toolKitRepository.existsByStorageLocationId(storageLocationId)) {
                    throw new IllegalArgumentException("Storage location ID " + storageLocationId + " already assigned to a tool kit");
                }
            }

            StorageLocationEntity storageLocation = storageLocationRepository
                    .findById(storageLocationId)
                    .orElseThrow(() -> new RecordNotFoundException("Storage location not found"));
            existingEntity.setStorageLocation(storageLocation);
        }
        if (requestDto.getStatus() != null) {
            existingEntity.setStatus(EquipmentStatus.valueOf(requestDto.getStatus().toUpperCase()));
        }
        if (requestDto.getCheckedOutBy() != null) {
            UserEntity checkedOutBy = userRepository
                    .findById(requestDto.getCheckedOutBy())
                    .orElseThrow(() -> new RecordNotFoundException("User not found"));
            existingEntity.setCheckedOutBy(checkedOutBy);
        }
        existingEntity.setCheckedOutDate(requestDto.getCheckedOutDate());
        existingEntity.setHasInspection(requestDto.getHasInspection());
        existingEntity.setComments(requestDto.getComments());

        //Tool-specific
        existingEntity.setToolType(requestDto.getToolType());
        existingEntity.setAtaCode(requestDto.getAtaCode());
        existingEntity.setPartNumber(requestDto.getPartNumber());
        existingEntity.setSerialNumber(requestDto.getSerialNumber());
        existingEntity.setManufacturer(requestDto.getManufacturer());
        Set<Long> aircraftTypeIds = requestDto.getApplicableAircraftTypeIds();
        List<AircraftTypeEntity> aircraftTypesList = aircraftTypeRepository.findAllById(aircraftTypeIds);
        if (aircraftTypesList.isEmpty()) {
            throw new RecordNotFoundException("No Aircraft Types found: " + aircraftTypeIds);
        }
        existingEntity.setApplicableAircraftTypes(new HashSet<>(aircraftTypesList));
        Set<Long> engineTypeIds = requestDto.getApplicableEngineTypeIds();
        List<EngineTypeEntity> engineTypesList = engineTypeRepository.findAllById(engineTypeIds);
        if (engineTypesList.isEmpty()) {
            throw new RecordNotFoundException("No Engine Types found: " + engineTypeIds);
        }
        existingEntity.setApplicableEngineTypes(new HashSet<>(engineTypesList));
        existingEntity.setIsCalibrated(requestDto.getIsCalibrated());
        if (requestDto.getInspectionId() != null) {
            InspectionEntity inspection = inspectionRepository
                    .findById(requestDto.getInspectionId())
                    .orElseThrow(() -> new RecordNotFoundException("Inspection not found"));
            existingEntity.setInspection(inspection);
        }
        if (requestDto.getToolKitId() != null) {
            ToolKitEntity toolKit = toolKitRepository
                    .findById(requestDto.getToolKitId())
                    .orElseThrow(() -> new RecordNotFoundException("Tool kit not found"));
            existingEntity.setToolKit(toolKit);
        }

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
/*
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
*/

    private ToolEntity getToolEntity(Long id) {
        return toolRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Tool " + id + " not found"));
    }
}