package nl.novi.deepwrench42.services;

import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitRequestDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.entities.*;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.helpers.FileStorageHelper;
import nl.novi.deepwrench42.mappers.ToolKitDTOMapper;
import nl.novi.deepwrench42.repository.*;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ToolKitService {
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;
    private final AircraftTypeRepository aircraftTypeRepository;
    private final EngineTypeRepository engineTypeRepository;
    private final InspectionRepository inspectionRepository;
    private final StorageLocationRepository storageLocationRepository;
    private final UserRepository userRepository;
    private final ToolKitDTOMapper toolKitDTOMapper;
    private final FileStorageHelper fileStorageHelper;

    public ToolKitService(ToolRepository toolRepository, ToolKitRepository toolKitRepository, AircraftTypeRepository aircraftTypeRepository, EngineTypeRepository engineTypeRepository, InspectionRepository inspectionRepository, StorageLocationRepository storageLocationRepository, UserRepository userRepository, ToolKitDTOMapper toolKitDTOMapper, FileStorageHelper fileStorageHelper) {
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        this.aircraftTypeRepository = aircraftTypeRepository;
        this.engineTypeRepository = engineTypeRepository;
        this.inspectionRepository = inspectionRepository;
        this.storageLocationRepository = storageLocationRepository;
        this.userRepository = userRepository;
        this.toolKitDTOMapper = toolKitDTOMapper;
        this.fileStorageHelper = fileStorageHelper;
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

        toolKitEntity.setEquipmentType(model.getEquipmentType());
        toolKitEntity.setItemId(model.getItemId());
        toolKitEntity.setName(model.getName());
        toolKitEntity.setPictureFileName(model.getPictureFileName());
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
            toolKitEntity.setStorageLocation(storageLocation);
        }
        if (model.getStatus() != null) {
            toolKitEntity.setStatus(EquipmentStatus.valueOf(model.getStatus().toUpperCase()));
        }
        if (model.getCheckedOutBy() != null) {
            UserEntity checkedOutBy = userRepository
                    .findById(model.getCheckedOutBy())
                    .orElseThrow(() -> new RecordNotFoundException("User not found"));
            toolKitEntity.setCheckedOutBy(checkedOutBy);
        }
        toolKitEntity.setCheckedOutDate(model.getCheckedOutDate());
        toolKitEntity.setHasInspection(model.getHasInspection());
        toolKitEntity.setComments(model.getComments());

        //Toolkit-specific
/*        Set<Long> toolIds = model.getKitContentsIds();
        List<ToolEntity> toolList = toolRepository.findAllById(toolIds);
        if (toolList.isEmpty()) {
            throw new RecordNotFoundException("No Tool found: " + toolIds);
        }
        toolKitEntity.setKitContents(new HashSet<>(toolList));*/

        toolKitEntity.setToolKitType(model.getToolKitType());
        toolKitEntity.setAtaCode(model.getAtaCode());
        toolKitEntity.setPartNumber(model.getPartNumber());
        toolKitEntity.setSerialNumber(model.getSerialNumber());
        toolKitEntity.setManufacturer(model.getManufacturer());
        Set<Long> aircraftTypeIds = model.getApplicableAircraftTypeIds();
        List<AircraftTypeEntity> aircraftTypesList = aircraftTypeRepository.findAllById(aircraftTypeIds);
        if (aircraftTypesList.isEmpty()) {
            throw new RecordNotFoundException("No Aircraft Types found: " + aircraftTypeIds);
        }
        toolKitEntity.setApplicableAircraftTypes(new HashSet<>(aircraftTypesList));
        Set<Long> engineTypeIds = model.getApplicableEngineTypeIds();
        List<EngineTypeEntity> engineTypesList = engineTypeRepository.findAllById(engineTypeIds);
        if (engineTypesList.isEmpty()) {
            throw new RecordNotFoundException("No Engine Types found: " + engineTypeIds);
        }
        toolKitEntity.setApplicableEngineTypes(new HashSet<>(engineTypesList));
        toolKitEntity.setIsCalibrated(model.getIsCalibrated());
        if (model.getInspectionId() != null) {
            InspectionEntity inspection = inspectionRepository
                    .findById(model.getInspectionId())
                    .orElseThrow(() -> new RecordNotFoundException("Inspection not found"));
            toolKitEntity.setInspection(inspection);
        }

        toolKitEntity = toolKitRepository.save(toolKitEntity);
        return toolKitDTOMapper.mapToDto(toolKitEntity);
    }

    @Transactional
    public ToolKitResponseDTO updateToolKit(Long id, ToolKitRequestDTO requestDto) {
        ToolKitEntity existingEntity = toolKitRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Tool " + id + " not found"));

        existingEntity.setEquipmentType(requestDto.getEquipmentType());
        existingEntity.setItemId(requestDto.getItemId());
        existingEntity.setName(requestDto.getName());
        existingEntity.setPictureFileName(requestDto.getPictureFileName());
        if (requestDto.getStorageLocation() == null) { throw new IllegalArgumentException("Storage Location required");
        }   else if (requestDto.getStorageLocation() != null) {
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
        if (existingEntity.getStatus() == EquipmentStatus.valueOf("CHECKED_OUT")){
            throw new IllegalArgumentException("Tool Kit " + existingEntity.getItemId() + " status is checked out");
        } else if (requestDto.getStatus() != null) {
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

        //Toolkit-specific
/*        if (requestDto.getKitContentsIds() != null) {
            Set<Long> toolIds = requestDto.getKitContentsIds();
            List<ToolEntity> tools = toolRepository.findAllById(toolIds);
            if (tools.isEmpty() && !toolIds.isEmpty()) {
                throw new RecordNotFoundException("No Tools found: " + toolIds);
            }

            existingEntity.getKitContents().clear();
            ToolKitEntity updatedExistingEntity = existingEntity;
            tools.forEach(tool -> {
                tool.setToolKit(updatedExistingEntity);
                updatedExistingEntity.getKitContents().add(tool);
            });
        }*/
        existingEntity.setToolKitType(requestDto.getToolKitType());
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

        existingEntity = toolKitRepository.save(existingEntity);
        return toolKitDTOMapper.mapToDto(existingEntity);
    }

    @Transactional
    public void deleteToolKit(Long id) {
        ToolKitEntity toolKit = getToolKitEntity(id);
        if (toolKit.getStatus() == EquipmentStatus.valueOf("CHECKED_OUT")){
            throw new IllegalArgumentException("Tool Kit " + toolKit.getItemId() + " status is checked out");}

        toolKit.setStorageLocation(null);
        toolKitRepository.delete(toolKit);
    }

    private ToolKitEntity getToolKitEntity(Long id) {
        return toolKitRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Tool Kit " + id + " not found"));
    }

    //picture services
    @Transactional
    public Resource getPictureFromTool(Long id){
        ToolKitEntity toolKit = getToolKitEntity(id);
        String toolKitItemId = toolKit.getItemId();
        String fileName = toolKit.getPictureFileName();
        if(fileName == null){
            throw new RecordNotFoundException("Tool Kit " + toolKitItemId + " has no picture in database.");
        }
        return fileStorageHelper.downLoadFile(fileName);
    }

    @Transactional
    public ToolKitResponseDTO assignPictureToTool(String fileName, Long id) {
        Optional<ToolKitEntity> existingToolKit = toolKitRepository.findById(id);

        if (existingToolKit.isPresent()) {
            ToolKitEntity toolKit = existingToolKit.get();
            if (toolKit.getPictureFileName() != null) {
                toolKit.setPictureFileName(fileName);
                toolKitRepository.save(toolKit);
                return toolKitDTOMapper.mapToDto(toolKit);
            } else {
                throw new RecordNotFoundException("Picture not found!");
            }
        } else {
            throw new RecordNotFoundException("Tool Kit not found!");
        }
    }
}
