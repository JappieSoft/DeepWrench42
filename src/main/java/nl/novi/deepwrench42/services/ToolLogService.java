package nl.novi.deepwrench42.services;

import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.tool.ToolRequestDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.dtos.toolLog.ToolLogResponseDTO;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.entities.ToolLogEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.ToolDTOMapper;
import nl.novi.deepwrench42.mappers.ToolLogDTOMapper;
import nl.novi.deepwrench42.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToolLogService {
    private final ToolLogRepository toolLogRepository;
    private final ToolKitRepository toolKitRepository;
    private final AircraftTypeRepository aircraftTypeRepository;
    private final EngineTypeRepository engineTypeRepository;
    private final InspectionRepository inspectionRepository;
    private final ToolLogDTOMapper toolLogDTOMapper;

    public ToolLogService(ToolLogRepository toolLogRepository, ToolKitRepository toolKitRepository, AircraftTypeRepository aircraftTypeRepository, EngineTypeRepository engineTypeRepository, InspectionRepository inspectionRepository, ToolLogDTOMapper toolLogDTOMapper) {
        this.toolLogRepository = toolLogRepository;
        this.toolKitRepository = toolKitRepository;
        this.aircraftTypeRepository = aircraftTypeRepository;
        this.engineTypeRepository = engineTypeRepository;
        this.inspectionRepository = inspectionRepository;
        this.toolLogDTOMapper = toolLogDTOMapper;
    }

    @Transactional
    public List<ToolLogResponseDTO> findAllToolLogs() {
        return toolLogDTOMapper.mapToDto(toolLogRepository.findAll());
    }

    @Transactional
    public ToolLogResponseDTO findToolLogById(Long id) {
        ToolLogEntity toolLogEntity = getToolLogEntity(id);
        return toolLogDTOMapper.mapToDto(toolLogEntity);
    }


    /*private void mapIdsToEntities(ToolEntity entity, ToolRequestDTO requestDto) {
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
    }*/

    private ToolLogEntity getToolLogEntity(Long id) {
        return toolLogRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Tool Log " + id + " not found"));
    }
}