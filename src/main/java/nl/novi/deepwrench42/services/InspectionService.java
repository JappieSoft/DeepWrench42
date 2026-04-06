package nl.novi.deepwrench42.services;

import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.equipment.*;
import nl.novi.deepwrench42.dtos.inspection.InspectionRequestDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionResponseDTO;
import nl.novi.deepwrench42.dtos.inspection.CompleteInspectionDTO;
import nl.novi.deepwrench42.entities.*;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.InspectionDTOMapper;
import nl.novi.deepwrench42.repository.InspectionRepository;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final InspectionDTOMapper inspectionDTOMapper;
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;

    public InspectionService(InspectionRepository inspectionRepository, InspectionDTOMapper inspectionDTOMapper, ToolRepository toolRepository, ToolKitRepository toolKitRepository) {
        this.inspectionRepository = inspectionRepository;
        this.inspectionDTOMapper = inspectionDTOMapper;
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        }

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

            if (dto.getToolId() != null && dto.getToolKitId() != null) {
                throw new IllegalArgumentException("Only one tool or tool kit allowed, not both at the same time.");
            }

            if (dto.getToolId() != null) {
                ToolEntity tool = toolRepository.getReferenceById(dto.getToolId());
                inspection.setTool(tool);

                tool.setHasInspection(true);
                if(Objects.equals(String.valueOf(dto.getInspectionType()), "CALIBRATION")){tool.setIsCalibrated(true);}
                toolRepository.save(tool);
            }
            if (dto.getToolKitId() != null) {
                ToolKitEntity kit = toolKitRepository.getReferenceById(dto.getToolKitId());
                inspection.setToolKit(kit);

                kit.setHasInspection(true);
                if(Objects.equals(String.valueOf(dto.getInspectionType()), "CALIBRATION")){kit.setIsCalibrated(true);}
                toolKitRepository.save(kit);
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

            tool.setHasInspection(true);
            if(Objects.equals(String.valueOf(requestDto.getInspectionType()), "CALIBRATION")){tool.setIsCalibrated(true);}
            toolRepository.save(tool);
        } else {
            existingEntity.setTool(null);
        }

        if (requestDto.getToolKitId() != null) {
            ToolKitEntity toolKit = toolKitRepository.getReferenceById(requestDto.getToolKitId());
            existingEntity.setToolKit(toolKit);

            toolKit.setHasInspection(true);
            if(Objects.equals(String.valueOf(requestDto.getInspectionType()), "CALIBRATION")){toolKit.setIsCalibrated(true);}
            toolKitRepository.save(toolKit);
        } else {
            existingEntity.setToolKit(null);
        }

        InspectionEntity saved = inspectionRepository.save(existingEntity);
        return inspectionDTOMapper.mapToDto(saved);
    }

        private InspectionEntity getInspectionEntity (Long id){
            InspectionEntity existingInspectionEntity = inspectionRepository.findById(id)
                    .orElseThrow(() -> new RecordNotFoundException("Inspection " + id + " not found"));
            return existingInspectionEntity;
        }

        @Transactional
        public void deleteInspection(Long id) {
            InspectionEntity inspection = getInspectionEntity(id);

            if (inspection.getTool() != null) {
                ToolEntity tool = inspection.getTool();
                tool.setInspection(null);
                tool.setHasInspection(false);
                tool.setIsCalibrated(false);
                toolRepository.save(tool);
            }
            if (inspection.getToolKit() != null) {
                ToolKitEntity kit = inspection.getToolKit();
                kit.setInspection(null);
                kit.setHasInspection(false);
                kit.setIsCalibrated(false);
                toolKitRepository.save(kit);
            }
            inspectionRepository.deleteById(id);
        }

        @Transactional
        public InspectionResponseDTO completeInspection(CompleteInspectionDTO requestDTO) {

            Optional<ToolEntity> selectedTool = toolRepository.findByItemId(requestDTO.getEquipmentItemId());
            if (selectedTool.isPresent()) {
                ToolEntity tool = selectedTool.get();
                Long inspectionId = tool.getInspection().getId();
                InspectionEntity existingEntity = getInspectionEntity(inspectionId);

                existingEntity.setInspectionDate(requestDTO.getInspectionDate());
                existingEntity.setInspectionPassed(requestDTO.getInspectionPassed());
                existingEntity.setComments(requestDTO.getComments());
                existingEntity.setNextDueDate(requestDTO.getNextDueDate());

                InspectionEntity saved = inspectionRepository.save(existingEntity);
                return inspectionDTOMapper.mapToDto(saved);
            }

            Optional<ToolKitEntity> selectedToolKit = toolKitRepository.findByItemId(requestDTO.getEquipmentItemId());
            if (selectedToolKit.isPresent()) {
                ToolKitEntity toolKit = selectedToolKit.get();
                Long inspectionId = toolKit.getInspection().getId();
                InspectionEntity existingEntity = getInspectionEntity(inspectionId);

                existingEntity.setInspectionDate(requestDTO.getInspectionDate());
                existingEntity.setInspectionPassed(requestDTO.getInspectionPassed());
                existingEntity.setComments(requestDTO.getComments());
                existingEntity.setNextDueDate(requestDTO.getNextDueDate());

                InspectionEntity saved = inspectionRepository.save(existingEntity);
                return inspectionDTOMapper.mapToDto(saved);
            }

            throw new IllegalArgumentException("No tool or toolkit found with itemId: " + requestDTO.getEquipmentItemId());
        }

        @Transactional
        public List<InspectionResponseDTO> findByInspectionDateBefore (LocalDateTime date){
            List<InspectionEntity> inspectionEntity = inspectionRepository.findByInspectionDateBefore(date);
            return inspectionDTOMapper.mapToDto(inspectionEntity);
        }

        @Transactional
        public List<InspectionResponseDTO> findByInspectionDateAfter (LocalDateTime date){
            List<InspectionEntity> inspectionEntity = inspectionRepository.findByInspectionDateAfter(date);
            return inspectionDTOMapper.mapToDto(inspectionEntity);
        }

        @Transactional
        public List<InspectionResponseDTO> findOverdueByNextDueDateBefore (LocalDateTime date){
            List<InspectionEntity> inspectionEntity = inspectionRepository.findOverdueByNextDueDateBefore(date);
            return inspectionDTOMapper.mapToDto(inspectionEntity);
        }
}
