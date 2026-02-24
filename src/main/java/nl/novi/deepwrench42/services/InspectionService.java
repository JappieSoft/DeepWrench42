package nl.novi.deepwrench42.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.equipment.EquipmentResponseDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionRequestDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionResponseDTO;
import nl.novi.deepwrench42.dtos.inspection.CompleteInspectionDTO;
import nl.novi.deepwrench42.entities.*;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.InspectionDTOMapper;
import nl.novi.deepwrench42.mappers.ToolDTOMapper;
import nl.novi.deepwrench42.mappers.ToolKitDTOMapper;
import nl.novi.deepwrench42.repository.InspectionRepository;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final InspectionDTOMapper inspectionDTOMapper;
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;
    private final ToolDTOMapper toolDTOMapper;
    private final ToolKitDTOMapper toolKitDTOMapper;

    public InspectionService(InspectionRepository inspectionRepository, InspectionDTOMapper inspectionDTOMapper, ToolRepository toolRepository, ToolKitRepository toolKitRepository, ToolDTOMapper toolDTOMapper, ToolKitDTOMapper toolKitDTOMapper) {
        this.inspectionRepository = inspectionRepository;
        this.inspectionDTOMapper = inspectionDTOMapper;
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        this.toolDTOMapper = toolDTOMapper;
        this.toolKitDTOMapper = toolKitDTOMapper; }

        @Transactional
        public List<InspectionResponseDTO> getAllInspections () {
            return inspectionDTOMapper.mapToDto(inspectionRepository.findAll());
        }

        @Transactional
        public InspectionResponseDTO findInspectionById (Long id){
            InspectionEntity inspectionEntity = getInspectionEntity(id);
            return inspectionDTOMapper.mapToDto(inspectionEntity);
        }

        @Transactional
        public InspectionResponseDTO createInspection(InspectionRequestDTO dto) {
            InspectionEntity inspection = inspectionDTOMapper.mapToEntity(dto);

            if (dto.getToolId() != null) {
                ToolEntity tool = toolRepository.getReferenceById(dto.getToolId());
                inspection.setTool(tool);
            }
            if (dto.getToolKitId() != null) {
                ToolKitEntity kit = toolKitRepository.getReferenceById(dto.getToolKitId());
                inspection.setToolKit(kit);
            }

            InspectionEntity saved = inspectionRepository.save(inspection);
            return inspectionDTOMapper.mapToDto(saved);
        }


    @Transactional
    public InspectionResponseDTO updateInspection(Long id, InspectionRequestDTO requestDto) {
        InspectionEntity existingEntity = getInspectionEntity(id);

        existingEntity.setInspectionDate(requestDto.getInspectionDate());
        existingEntity.setInspectionType(requestDto.getInspectionType());
        existingEntity.setInspectionPassed(requestDto.getInspectionPassed());
        existingEntity.setComments(requestDto.getComments());
        existingEntity.setNextDueDate(requestDto.getNextDueDate());
        existingEntity.setInspectionInterval(requestDto.getInspectionInterval());

        if (requestDto.getToolId() != null) {
            ToolEntity tool = toolRepository.getReferenceById(requestDto.getToolId());
            existingEntity.setTool(tool);
        } else {
            existingEntity.setTool(null);
        }

        if (requestDto.getToolKitId() != null) {
            ToolKitEntity toolKit = toolKitRepository.getReferenceById(requestDto.getToolKitId());
            existingEntity.setToolKit(toolKit);
        } else {
            existingEntity.setToolKit(null);
        }

        InspectionEntity saved = inspectionRepository.save(existingEntity);
        return inspectionDTOMapper.mapToDto(saved);
    }


        private InspectionEntity getInspectionEntity (Long id){
            InspectionEntity existingInspectionEntity = inspectionRepository.findById(id)
                    .orElseThrow(() -> new RecordNotFoundException("inspection " + id + " not found"));
            return existingInspectionEntity;
        }

        @Transactional
        public void deleteInspection (Long id){
            InspectionEntity inspection = getInspectionEntity(id);
            inspectionRepository.deleteById(id);
        }


//some other ideas i'm working on to test with postman later:

        public EquipmentResponseDTO getEquipmentForInspection (Long inspectionId){
            InspectionEntity inspection = inspectionRepository.findById(inspectionId)
                    .orElseThrow(() -> new EntityNotFoundException("Inspection not found: " + inspectionId));

            if (inspection.getTool() != null) {
                return toolDTOMapper.mapToDto(inspection.getTool());
            }
            if (inspection.getToolKit() != null) {
                return toolKitDTOMapper.mapToDto(inspection.getToolKit());
            }
            throw new IllegalStateException("Inspection " + inspectionId + " has no associated equipment");
        }


    /*@Transactional
    public InspectionResponseDTO completeInspection(Long inspectionId, CompleteInspectionDTO requestDto) {
        InspectionEntity inspection = inspectionRepository.findById(inspectionId)
                .orElseThrow(() -> new EntityNotFoundException("Inspection not found: " + inspectionId));

        // Update inspection fields (single source of truth)
        inspection.setInspectionDate(requestDto.getInspectionDate());
        inspection.setInspectionPassed(requestDto.getInspectionPassed());
        inspection.setComments(requestDto.getComments());
        inspection.setNextDueDate(requestDto.getNextDueDate());

        // Update associated equipment status
        if (inspection.getTool() != null) {
            updateToolKitAfterInspection(inspection.getTool(), requestDto);
        } else if (inspection.getToolKit() != null) {
            updateToolKitAfterInspection(inspection.getToolKit(), requestDto);
        }

        return inspectionDTOMapper.mapToDto(inspectionRepository.save(inspection));
    }

        private void updateToolKitAfterInspection (ToolKitEntity toolKit, CompleteInspectionDTO requestDto){
            if (Boolean.FALSE.equals(requestDto.getInspectionPassed())) {
                toolKit.setStatus(EquipmentStatus.UNSERVICEABLE);
            }
            toolKit.setEditDate(LocalDateTime.now());
            toolKitRepository.save(toolKit);
        }
*/
}
