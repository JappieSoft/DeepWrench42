package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.inspection.InspectionRequestDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionResponseDTO;
import nl.novi.deepwrench42.entities.InspectionEntity;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.InspectionDTOMapper;
import nl.novi.deepwrench42.repository.InspectionRepository;
import nl.novi.deepwrench42.repository.ToolRepository;

import java.util.List;

public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final ToolRepository toolRepository;
    private final InspectionDTOMapper inspectionDTOMapper;


    public InspectionService(InspectionRepository inspectionRepository, ToolRepository toolRepository, InspectionDTOMapper inspectionDTOMapper) {
        this.inspectionRepository = inspectionRepository;
        this.toolRepository = toolRepository;
        this.inspectionDTOMapper = inspectionDTOMapper;
    }

    public List<InspectionResponseDTO> findAllInspections() {
        return inspectionDTOMapper.mapToDto(inspectionRepository.findAll());
    }

    public InspectionResponseDTO findInspectionById(Long id) {
        InspectionEntity inspectionEntity = getInspectionEntity(id);
        return inspectionDTOMapper.mapToDto(inspectionEntity);
    }

    public InspectionResponseDTO createInspection(InspectionRequestDTO model) {
        InspectionEntity inspectionEntity = inspectionDTOMapper.mapToEntity(model);
        inspectionEntity = inspectionRepository.save(inspectionEntity);
        return inspectionDTOMapper.mapToDto(inspectionEntity);
    }

    public InspectionResponseDTO updateInspection(Long id, InspectionRequestDTO requestDto) {
        InspectionEntity existingEntity = getInspectionEntity(id);
        ToolEntity tool = toolRepository.getReferenceById(requestDto.getToolId());

        existingEntity.setInspectionDate(requestDto.getInspectionDate());
        existingEntity.setInspectionType(requestDto.getInspectionType());
        existingEntity.setNextDueDate(requestDto.getNextDueDate());
        existingEntity.setInspectionInterval(requestDto.getInspectionInterval());
        existingEntity.setTool(tool);

        existingEntity = inspectionRepository.save(existingEntity);
        return inspectionDTOMapper.mapToDto(existingEntity);
    }


    private InspectionEntity getInspectionEntity(Long id) {
        InspectionEntity existingInspectionEntity = inspectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("inspection " + id +" not found"));
        return existingInspectionEntity;
    }

    public void deleteInspection(Long id) {
        InspectionEntity inspection = getInspectionEntity(id);
        inspectionRepository.deleteById(id);
    }
}