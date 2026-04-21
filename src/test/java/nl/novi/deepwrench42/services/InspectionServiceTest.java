package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.inspection.CompleteInspectionDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionRequestDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionResponseDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.entities.*;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.InspectionDTOMapper;
import nl.novi.deepwrench42.repository.InspectionRepository;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InspectionServiceTest {

    @Mock
    InspectionRepository inspectionRepository;
    @Mock
    ToolRepository toolRepository;
    @Mock
    ToolKitRepository toolKitRepository;
    @Mock
    InspectionDTOMapper inspectionDTOMapper;

    @InjectMocks
    InspectionService inspectionService;

    private ToolEntity tool1;
    private ToolEntity tool2;
    private ToolKitEntity toolKit1;
    private ToolKitEntity toolKit2;

    private ToolResponseDTO toolResponseDTO1;
    private ToolResponseDTO toolResponseDTO2;
    private ToolKitResponseDTO toolResponseKitDTO1;
    private ToolKitResponseDTO toolResponseKitDTO2;
    private InspectionEntity inspectionEntity1;
    private InspectionEntity inspectionEntity2;
    private InspectionRequestDTO inspectionRequestDTO1;
    private InspectionRequestDTO inspectionRequestDTO2;
    private InspectionResponseDTO inspectionResponseDTO1;
    private InspectionResponseDTO inspectionResponseDTO2;
    private CompleteInspectionDTO completeInspectionDTO;

    @BeforeEach
    void setUp() {
        tool1 = new ToolEntity();
        tool1.setId(1L);
        tool1.setEquipmentType(EquipmentType.TOOL);
        tool1.setItemId("TEST001");
        tool1.setName("ToolTest");
        tool1.setHasInspection(false);
        tool1.setComments("test comments");
        tool1.setIsCalibrated(false);
        tool1.setInspection(null);

        tool2 = new ToolEntity();
        tool2.setId(2L);
        tool2.setEquipmentType(EquipmentType.TOOL);
        tool2.setItemId("TEST2");
        tool2.setName("SecondTool");
        tool2.setHasInspection(false);
        tool2.setComments("comments of second tool");
        tool2.setIsCalibrated(false);
        tool2.setInspection(null);

        toolKit1 = new ToolKitEntity();
        toolKit1.setEquipmentType(EquipmentType.TOOLKIT);
        toolKit1.setItemId("Kit001");
        toolKit1.setName("KitTest");
        toolKit1.setHasInspection(false);
        toolKit1.setComments("Kit test comments");
        toolKit1.setIsCalibrated(false);
        toolKit1.setInspection(null);

        toolKit2 = new ToolKitEntity();
        toolKit2.setEquipmentType(EquipmentType.TOOLKIT);
        toolKit2.setItemId("Kit002");
        toolKit2.setName("KitTwo");
        toolKit2.setHasInspection(false);
        toolKit2.setComments("Kit 2 comments");
        toolKit2.setIsCalibrated(false);
        toolKit2.setInspection(null);

        toolResponseDTO1 = new ToolResponseDTO();
        toolResponseDTO1.setId(1L);
        toolResponseDTO1.setEquipmentType(EquipmentType.TOOL);
        toolResponseDTO1.setItemId("TEST001");
        toolResponseDTO1.setName("ToolTest");
        toolResponseDTO1.setHasInspection(false);
        toolResponseDTO1.setComments("test comments");
        toolResponseDTO1.setIsCalibrated(false);
        toolResponseDTO1.setInspection(null);

        toolResponseDTO2 = new ToolResponseDTO();
        toolResponseDTO2.setId(2L);
        toolResponseDTO2.setEquipmentType(EquipmentType.TOOL);
        toolResponseDTO2.setItemId("TEST2");
        toolResponseDTO2.setName("SecondTool");
        toolResponseDTO2.setHasInspection(false);
        toolResponseDTO2.setComments("comments of second tool");
        toolResponseDTO2.setIsCalibrated(false);
        toolResponseDTO2.setInspection(null);

        toolResponseKitDTO1 = new ToolKitResponseDTO();
        toolResponseKitDTO1.setEquipmentType(EquipmentType.TOOLKIT);
        toolResponseKitDTO1.setItemId("Kit001");
        toolResponseKitDTO1.setName("KitTest");
        toolResponseKitDTO1.setHasInspection(false);
        toolResponseKitDTO1.setComments("Kit test comments");
        toolResponseKitDTO1.setIsCalibrated(false);
        toolResponseKitDTO1.setInspection(null);

        toolResponseKitDTO2 = new ToolKitResponseDTO();
        toolResponseKitDTO2.setEquipmentType(EquipmentType.TOOLKIT);
        toolResponseKitDTO2.setItemId("Kit002");
        toolResponseKitDTO2.setName("KitTwo");
        toolResponseKitDTO2.setHasInspection(false);
        toolResponseKitDTO2.setComments("Kit 2 comments");
        toolResponseKitDTO2.setIsCalibrated(false);
        toolResponseKitDTO2.setInspection(null);

        inspectionEntity1 = new InspectionEntity();
        inspectionEntity1.setId(1L);
        inspectionEntity1.setInspectionDate(LocalDateTime.of(2026, 1, 1, 1, 1));
        inspectionEntity1.setInspectionType(InspectionType.CALIBRATION);
        inspectionEntity1.setInspectionPassed(true);
        inspectionEntity1.setComments("New inspection");
        inspectionEntity1.setNextDueDate(LocalDateTime.of(2027, 1, 1, 1, 1));
        inspectionEntity1.setInspectionInterval(12);
        inspectionEntity1.setTool(tool1);
        inspectionEntity1.setToolKit(null);

        inspectionEntity2 = new InspectionEntity();
        inspectionEntity2.setId(2L);
        inspectionEntity2.setInspectionDate(LocalDateTime.of(2026, 12, 12, 12, 12));
        inspectionEntity2.setInspectionType(InspectionType.CALIBRATION);
        inspectionEntity2.setInspectionPassed(true);
        inspectionEntity2.setComments("New inspection");
        inspectionEntity2.setNextDueDate(LocalDateTime.of(2027, 1, 1, 1, 1));
        inspectionEntity2.setInspectionInterval(12);
        inspectionEntity2.setTool(null);
        inspectionEntity2.setToolKit(toolKit1);

        inspectionRequestDTO1 = new InspectionRequestDTO();
        inspectionRequestDTO1.setInspectionDate(LocalDateTime.of(2026, 1, 1, 1, 1));
        inspectionRequestDTO1.setInspectionType(InspectionType.CALIBRATION);
        inspectionRequestDTO1.setInspectionPassed(true);
        inspectionRequestDTO1.setComments("New inspection");
        inspectionRequestDTO1.setNextDueDate(LocalDateTime.of(2027, 1, 1, 1, 1));
        inspectionRequestDTO1.setInspectionInterval(12);
        inspectionRequestDTO1.setToolId(1L);
        inspectionRequestDTO1.setToolKitId(null);

        inspectionResponseDTO1 = new InspectionResponseDTO();
        inspectionResponseDTO1.setId(1L);
        inspectionResponseDTO1.setInspectionDate(LocalDateTime.of(2026, 1, 1, 1, 1));
        inspectionResponseDTO1.setInspectionType(InspectionType.CALIBRATION);
        inspectionResponseDTO1.setInspectionPassed(true);
        inspectionResponseDTO1.setComments("New inspection");
        inspectionResponseDTO1.setNextDueDate(LocalDateTime.of(2027, 1, 1, 1, 1));
        inspectionResponseDTO1.setInspectionInterval(12);
        inspectionResponseDTO1.setToolId(1L);
        inspectionResponseDTO1.setToolKitId(null);
        inspectionResponseDTO1.setEquipmentItemId("TEST001");

        inspectionRequestDTO2 = new InspectionRequestDTO();
        inspectionRequestDTO2.setInspectionDate(LocalDateTime.of(2026, 12, 12, 12, 12));
        inspectionRequestDTO2.setInspectionType(InspectionType.CALIBRATION);
        inspectionRequestDTO2.setInspectionPassed(true);
        inspectionRequestDTO2.setComments("New Kit inspection");
        inspectionRequestDTO2.setNextDueDate(LocalDateTime.of(2027, 1, 1, 1, 1));
        inspectionRequestDTO2.setInspectionInterval(12);
        inspectionRequestDTO2.setToolId(null);
        inspectionRequestDTO2.setToolKitId(1L);

        inspectionResponseDTO2 = new InspectionResponseDTO();
        inspectionResponseDTO2.setId(2L);
        inspectionResponseDTO2.setInspectionDate(LocalDateTime.of(2026, 12, 12, 12, 12));
        inspectionResponseDTO2.setInspectionType(InspectionType.CALIBRATION);
        inspectionResponseDTO2.setInspectionPassed(true);
        inspectionResponseDTO2.setComments("New inspection");
        inspectionResponseDTO2.setNextDueDate(LocalDateTime.of(2027, 1, 1, 1, 1));
        inspectionResponseDTO2.setInspectionInterval(12);
        inspectionResponseDTO2.setToolId(null);
        inspectionResponseDTO2.setToolKitId(1L);
        inspectionResponseDTO2.setEquipmentItemId("Kit001");

        completeInspectionDTO = new CompleteInspectionDTO();
    }

    @Nested
    @DisplayName("1. Get Testing")
    class GetFunctionsTesting {
        @Test
        @DisplayName("Find all inspections, Working")
        void findAllInspections_shouldReturnList() {
            // Arrange
            InspectionEntity inspectionEntity2 = new InspectionEntity();
            inspectionEntity2.setId(2L);
            inspectionEntity2.setInspectionDate(LocalDateTime.of(2020, 10, 20, 10, 20));
            inspectionEntity2.setInspectionType(InspectionType.VISUAL_INSPECTION);
            inspectionEntity2.setInspectionPassed(FALSE);
            inspectionEntity2.setComments("Kit inspection");
            inspectionEntity2.setNextDueDate(LocalDateTime.of(2030, 10, 10, 10, 10));
            inspectionEntity2.setInspectionInterval(10);
            inspectionEntity2.setTool(null);
            inspectionEntity2.setToolKit(toolKit2);
            InspectionResponseDTO inspectionResponseDTO2 = new InspectionResponseDTO();
            inspectionResponseDTO2.setId(2L);
            inspectionResponseDTO2.setInspectionDate(LocalDateTime.of(2020, 10, 20, 10, 20));
            inspectionResponseDTO2.setInspectionType(InspectionType.VISUAL_INSPECTION);
            inspectionResponseDTO2.setInspectionPassed(FALSE);
            inspectionResponseDTO2.setComments("Kit inspection");
            inspectionResponseDTO2.setNextDueDate(LocalDateTime.of(2030, 10, 10, 10, 10));
            inspectionResponseDTO2.setInspectionInterval(10);
            inspectionResponseDTO2.setToolId(null);
            inspectionResponseDTO2.setToolKitId(1L);
            inspectionResponseDTO2.setEquipmentItemId("Kit001");

            when(inspectionRepository.findAll()).thenReturn(Arrays.asList(inspectionEntity1, inspectionEntity2));
            when(inspectionDTOMapper.mapToDto(List.of(inspectionEntity1, inspectionEntity2))).thenReturn(List.of(inspectionResponseDTO1, inspectionResponseDTO2));

            // Act
            List<InspectionResponseDTO> result = inspectionService.getAllInspections();

            // Assert
            assertEquals(2, result.size());
            assertEquals(inspectionResponseDTO1, result.get(0));
            assertEquals(inspectionResponseDTO2, result.get(1));
            verify(inspectionRepository, times(1)).findAll();
            verify(inspectionDTOMapper, times(1)).mapToDto(List.of(inspectionEntity1, inspectionEntity2));
        }

        @Test
        @DisplayName("Find inspection id 1, Working")
        void findInspectionById_shouldReturnInspectionResponseDTO_whenInspectionExists() {
            // Arrange
            when(inspectionRepository.findById(1L)).thenReturn(Optional.of(inspectionEntity1));
            when(inspectionDTOMapper.mapToDto(inspectionEntity1)).thenReturn(inspectionResponseDTO1);

            // Act
            InspectionResponseDTO result = inspectionService.findInspectionById(1L);

            // Assert
            assertEquals(1, result.getToolId());
            assertEquals(inspectionResponseDTO1, result);
        }

        @Test
        @DisplayName("Find inspection id 999, Aircraft Not Found / Throw Exception")
        void findInspectionById_shouldThrow_whenInspectionDoesNotExist() {
            // Arrange
            Long id = 999L;
            when(inspectionRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                    () -> inspectionService.findInspectionById(id));
            assertTrue(ex.getMessage().contains("Inspection 999 not found"));
        }
    }

    @Nested
    @DisplayName("2. Create Testing")
    class CreateFunctionsTesting {
        @Test
        @DisplayName("Create new Tool Calibration Inspection, Working")
        void createInspection_shouldReturnCreatedToolInspection_IsCalibratedAndHasInspection() {
            // Arrange
            ToolEntity updatedTool1 = new ToolEntity();
            updatedTool1.setId(1L);
            updatedTool1.setHasInspection(true);
            updatedTool1.setComments("test comments");
            updatedTool1.setIsCalibrated(true);
            updatedTool1.setInspection(inspectionEntity1);

            when(inspectionDTOMapper.mapToEntity(inspectionRequestDTO1)).thenReturn(inspectionEntity1);
            when(toolRepository.findById(1L)).thenReturn(Optional.of(tool1));
            when(toolRepository.save(tool1)).thenReturn(updatedTool1);
            when(inspectionRepository.save(inspectionEntity1)).thenReturn(inspectionEntity1);
            when(inspectionDTOMapper.mapToDto(inspectionEntity1)).thenReturn(inspectionResponseDTO1);

            // Act
            InspectionResponseDTO result = inspectionService.createInspection(inspectionRequestDTO1);

            //Assert
            assertEquals(inspectionResponseDTO1, result);
            assertEquals(1L, result.getToolId());
            assertTrue(tool1.getHasInspection());
            assertTrue(tool1.getIsCalibrated());
            verify(inspectionDTOMapper, times(1)).mapToEntity(inspectionRequestDTO1);
            verify(toolRepository, times(1)).findById(1L);
            verify(inspectionRepository, times(1)).save(inspectionEntity1);
            verify(inspectionDTOMapper, times(1)).mapToDto(inspectionEntity1);
        }

        @Test
        @DisplayName("Create new Tool Not-Calibrated Inspection, Working")
        void createInspection_shouldReturnCreatedToolInspection_HasInspection() {
            // Arrange
            InspectionRequestDTO inspectionRequestDTO = new InspectionRequestDTO();
            inspectionRequestDTO.setToolId(1L);
            inspectionRequestDTO.setToolKitId(null);

            ToolEntity updatedTool1 = new ToolEntity();
            updatedTool1.setId(1L);
            updatedTool1.setHasInspection(true);
            updatedTool1.setIsCalibrated(false);

            when(inspectionDTOMapper.mapToEntity(inspectionRequestDTO)).thenReturn(inspectionEntity1);
            when(toolRepository.findById(1L)).thenReturn(Optional.of(tool1));
            when(toolRepository.save(tool1)).thenReturn(updatedTool1);
            when(inspectionRepository.save(inspectionEntity1)).thenReturn(inspectionEntity1);
            when(inspectionDTOMapper.mapToDto(inspectionEntity1)).thenReturn(inspectionResponseDTO1);

            // Act
            InspectionResponseDTO result = inspectionService.createInspection(inspectionRequestDTO);

            //Assert
            assertEquals(inspectionResponseDTO1, result);
            assertEquals(1L, result.getToolId());
            assertTrue(tool1.getHasInspection());
            assertFalse(tool1.getIsCalibrated());
            verify(inspectionDTOMapper, times(1)).mapToEntity(inspectionRequestDTO);
            verify(toolRepository, times(1)).findById(1L);
            verify(inspectionRepository, times(1)).save(inspectionEntity1);
            verify(inspectionDTOMapper, times(1)).mapToDto(inspectionEntity1);
        }

        @Test
        @DisplayName("Create new ToolKit Calibration Inspection, Working")
        void createInspection_shouldReturnCreatedToolKitInspection_IsCalibratedAndHasInspection() {
            // Arrange
            ToolKitEntity updatedToolKit1 = new ToolKitEntity();
            updatedToolKit1.setId(1L);
            updatedToolKit1.setHasInspection(true);
            updatedToolKit1.setComments("kit comments");
            updatedToolKit1.setIsCalibrated(true);
            updatedToolKit1.setInspection(inspectionEntity2);

            when(inspectionDTOMapper.mapToEntity(inspectionRequestDTO2)).thenReturn(inspectionEntity2);
            when(toolKitRepository.findById(1L)).thenReturn(Optional.of(toolKit1));
            when(toolKitRepository.save(toolKit1)).thenReturn(updatedToolKit1);
            when(inspectionRepository.save(inspectionEntity2)).thenReturn(inspectionEntity2);
            when(inspectionDTOMapper.mapToDto(inspectionEntity2)).thenReturn(inspectionResponseDTO2);

            // Act
            InspectionResponseDTO result = inspectionService.createInspection(inspectionRequestDTO2);

            //Assert
            assertEquals(inspectionResponseDTO2, result);
            assertEquals(1L, result.getToolKitId());
            assertTrue(toolKit1.getHasInspection());
            assertTrue(toolKit1.getIsCalibrated());
            verify(inspectionDTOMapper, times(1)).mapToEntity(inspectionRequestDTO2);
            verify(toolKitRepository, times(1)).findById(1L);
            verify(inspectionRepository, times(1)).save(inspectionEntity2);
            verify(inspectionDTOMapper, times(1)).mapToDto(inspectionEntity2);
        }

        @Test
        @DisplayName("Create new ToolKit Not-Calibrated Inspection, Working")
        void createInspection_shouldReturnCreatedToolKitInspection_HasInspection() {
            // Arrange
            InspectionRequestDTO inspectionRequestDTO = new InspectionRequestDTO();
            inspectionRequestDTO.setToolId(null);
            inspectionRequestDTO.setToolKitId(1L);

            ToolKitEntity updatedToolKit1 = new ToolKitEntity();
            updatedToolKit1.setId(1L);
            updatedToolKit1.setHasInspection(true);
            updatedToolKit1.setComments("kit comments");
            updatedToolKit1.setIsCalibrated(false);
            updatedToolKit1.setInspection(inspectionEntity2);

            when(inspectionDTOMapper.mapToEntity(inspectionRequestDTO)).thenReturn(inspectionEntity2);
            when(toolKitRepository.findById(1L)).thenReturn(Optional.of(toolKit1));
            when(toolKitRepository.save(toolKit1)).thenReturn(updatedToolKit1);
            when(inspectionRepository.save(inspectionEntity2)).thenReturn(inspectionEntity2);
            when(inspectionDTOMapper.mapToDto(inspectionEntity2)).thenReturn(inspectionResponseDTO2);

            // Act
            InspectionResponseDTO result = inspectionService.createInspection(inspectionRequestDTO);

            //Assert
            assertEquals(inspectionResponseDTO2, result);
            assertEquals(1L, result.getToolKitId());
            assertTrue(toolKit1.getHasInspection());
            assertFalse(toolKit1.getIsCalibrated());
            verify(inspectionDTOMapper, times(1)).mapToEntity(inspectionRequestDTO);
            verify(toolKitRepository, times(1)).findById(1L);
            verify(inspectionRepository, times(1)).save(inspectionEntity2);
            verify(inspectionDTOMapper, times(1)).mapToDto(inspectionEntity2);
        }

        @Test
        @DisplayName("Create new Tool & ToolKit calibration / Throw Exception")
        void createAircraft_shouldThrow_AircraftType_NotFoundException() {
            // Arrange
            InspectionRequestDTO inspectionRequestDTO = new InspectionRequestDTO();
            inspectionRequestDTO.setToolId(1L);
            inspectionRequestDTO.setToolKitId(1L);
            when(inspectionDTOMapper.mapToEntity(inspectionRequestDTO)).thenReturn(inspectionEntity1);

            //Act & Assert
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> inspectionService.createInspection(inspectionRequestDTO));
            assertTrue(ex.getMessage().contains("Only one tool or tool kit allowed, not both at the same time."));
        }
    }

    @Nested
    @DisplayName("3. Update Testing")
    class UpdateFunctionsTesting {
        @Test
        @DisplayName("Update Tool 1 inspection to visual (complete), Working")
        void updateInspection_shouldReturnUpdatedToolInspection_ToHasInspection() {
            // Arrange
            Long id = 1L;

            ToolEntity updatedTool = new ToolEntity();
            updatedTool.setId(2L);
            updatedTool.setHasInspection(true);
            updatedTool.setComments("test update comments");
            updatedTool.setIsCalibrated(false);
            updatedTool.setInspection(inspectionEntity1);

            InspectionRequestDTO updateDto = new InspectionRequestDTO();
            updateDto.setInspectionDate(LocalDateTime.of(2025, 12, 12, 12, 12));
            updateDto.setInspectionType(InspectionType.VISUAL_INSPECTION);
            updateDto.setInspectionPassed(true);
            updateDto.setComments("Change to Visual inspection");
            updateDto.setNextDueDate(LocalDateTime.of(2026, 6, 6, 6, 6));
            updateDto.setInspectionInterval(6);
            updateDto.setToolId(2L);
            updateDto.setToolKitId(null);

            InspectionResponseDTO updatedResponseDTO = new InspectionResponseDTO();
            updatedResponseDTO.setId(1L);
            updatedResponseDTO.setInspectionDate(LocalDateTime.of(2025, 12, 12, 12, 12));
            updatedResponseDTO.setInspectionType(InspectionType.VISUAL_INSPECTION);
            updatedResponseDTO.setInspectionPassed(true);
            updatedResponseDTO.setComments("Change to Visual inspection");
            updatedResponseDTO.setNextDueDate(LocalDateTime.of(2026, 6, 6, 6, 6));
            updatedResponseDTO.setInspectionInterval(12);
            updatedResponseDTO.setToolId(2L);
            updatedResponseDTO.setToolKitId(null);
            updatedResponseDTO.setEquipmentItemId("TEST001");

            when(inspectionRepository.findById(id)).thenReturn(Optional.of(inspectionEntity1));
            when(toolRepository.findById(2L)).thenReturn(Optional.of(tool2));
            when(toolRepository.save(tool2)).thenReturn(updatedTool);
            when(inspectionRepository.save(inspectionEntity1)).thenReturn(inspectionEntity1);
            when(inspectionDTOMapper.mapToDto(inspectionEntity1)).thenReturn(updatedResponseDTO);

            // Act
            InspectionResponseDTO result = inspectionService.updateInspection(id, updateDto);

            //Assert
            assertEquals(updatedResponseDTO, result);
            assertEquals(2L, result.getToolId());
            assertTrue(tool2.getHasInspection());
            assertFalse(tool2.getIsCalibrated());
            verify(inspectionRepository, times(1)).findById(id);
            verify(toolRepository, times(1)).findById(2L);
            verify(inspectionRepository, times(1)).save(inspectionEntity1);
            verify(inspectionDTOMapper, times(1)).mapToDto(inspectionEntity1);
        }

        @Test
        @DisplayName("Update Tool 1 inspection to calibration (compact), Working")
        void updateInspection_shouldReturnUpdatedToolInspection_ToIsCalibratedAndHasInspection() {
            // Arrange
            Long id = 1L;

            ToolEntity updatedTool = new ToolEntity();
            updatedTool.setId(2L);
            updatedTool.setHasInspection(true);
            updatedTool.setComments("test comments");
            updatedTool.setIsCalibrated(true);
            updatedTool.setInspection(inspectionEntity1);

            InspectionRequestDTO updateDto = new InspectionRequestDTO();
            updateDto.setInspectionType(InspectionType.CALIBRATION);
            updateDto.setInspectionPassed(true);
            updateDto.setComments("Change to Calibration inspection");
            updateDto.setToolId(2L);
            updateDto.setToolKitId(null);

            InspectionResponseDTO updatedResponseDTO = new InspectionResponseDTO();
            updatedResponseDTO.setId(1L);
            updatedResponseDTO.setInspectionType(InspectionType.CALIBRATION);
            updatedResponseDTO.setInspectionPassed(true);
            updatedResponseDTO.setComments("Change to Calibration inspection");
            updatedResponseDTO.setToolId(2L);
            updatedResponseDTO.setToolKitId(null);
            updatedResponseDTO.setEquipmentItemId("TEST001");

            when(inspectionRepository.findById(id)).thenReturn(Optional.of(inspectionEntity1));
            when(toolRepository.findById(2L)).thenReturn(Optional.of(tool2));
            when(toolRepository.save(tool2)).thenReturn(updatedTool);
            when(inspectionRepository.save(inspectionEntity1)).thenReturn(inspectionEntity1);
            when(inspectionDTOMapper.mapToDto(inspectionEntity1)).thenReturn(updatedResponseDTO);

            // Act
            InspectionResponseDTO result = inspectionService.updateInspection(id, updateDto);

            //Assert
            assertEquals(updatedResponseDTO, result);
            assertEquals(2L, result.getToolId());
            assertTrue(tool2.getHasInspection());
            assertTrue(tool2.getIsCalibrated());
            verify(inspectionRepository, times(1)).findById(id);
            verify(toolRepository, times(1)).findById(2L);
            verify(inspectionRepository, times(1)).save(inspectionEntity1);
            verify(inspectionDTOMapper, times(1)).mapToDto(inspectionEntity1);
        }

        @Test
        @DisplayName("Update Tool Kit 1 inspection to visual (complete), Working")
        void updateInspection_shouldReturnUpdatedToolKitInspection_ToHasInspection() {
            // Arrange
            Long id = 2L;

            ToolKitEntity updatedToolKit = new ToolKitEntity();
            updatedToolKit.setId(2L);
            updatedToolKit.setHasInspection(true);
            updatedToolKit.setComments("kit update comments");
            updatedToolKit.setIsCalibrated(false);
            updatedToolKit.setInspection(inspectionEntity2);

            InspectionRequestDTO updateDto = new InspectionRequestDTO();
            updateDto.setInspectionDate(LocalDateTime.of(2024, 4, 4, 4, 4));
            updateDto.setInspectionType(InspectionType.VISUAL_INSPECTION);
            updateDto.setInspectionPassed(true);
            updateDto.setComments("Change Kit to Visual inspection");
            updateDto.setNextDueDate(LocalDateTime.of(2027, 7, 7, 7, 7));
            updateDto.setInspectionInterval(6);
            updateDto.setToolId(null);
            updateDto.setToolKitId(2L);

            InspectionResponseDTO updatedResponseDTO = new InspectionResponseDTO();
            updatedResponseDTO.setId(2L);
            updatedResponseDTO.setInspectionDate(LocalDateTime.of(2024, 4, 4, 4, 4));
            updatedResponseDTO.setInspectionType(InspectionType.VISUAL_INSPECTION);
            updatedResponseDTO.setInspectionPassed(true);
            updatedResponseDTO.setComments("Change Kit to Visual inspection");
            updatedResponseDTO.setNextDueDate(LocalDateTime.of(2027, 7, 7, 7, 7));
            updatedResponseDTO.setInspectionInterval(36);
            updatedResponseDTO.setToolId(null);
            updatedResponseDTO.setToolKitId(2L);
            updatedResponseDTO.setEquipmentItemId("Kit002");

            when(inspectionRepository.findById(id)).thenReturn(Optional.of(inspectionEntity2));
            when(toolKitRepository.findById(2L)).thenReturn(Optional.of(toolKit2));
            when(toolKitRepository.save(toolKit2)).thenReturn(updatedToolKit);
            when(inspectionRepository.save(inspectionEntity2)).thenReturn(inspectionEntity2);
            when(inspectionDTOMapper.mapToDto(inspectionEntity2)).thenReturn(updatedResponseDTO);

            // Act
            InspectionResponseDTO result = inspectionService.updateInspection(id, updateDto);

            //Assert
            assertEquals(updatedResponseDTO, result);
            assertEquals(2L, result.getToolKitId());
            assertTrue(toolKit2.getHasInspection());
            assertFalse(toolKit2.getIsCalibrated());
            verify(inspectionRepository, times(1)).findById(id);
            verify(toolKitRepository, times(1)).findById(2L);
            verify(inspectionRepository, times(1)).save(inspectionEntity2);
            verify(inspectionDTOMapper, times(1)).mapToDto(inspectionEntity2);
        }

        @Test
        @DisplayName("Update Tool Kit 1 inspection to calibration (compact), Working")
        void updateInspection_shouldReturnUpdatedToolKitInspection_ToIsCalibratedAndHasInspection() {
            // Arrange
            Long id = 2L;

            ToolKitEntity updatedToolKit = new ToolKitEntity();
            updatedToolKit.setId(2L);
            updatedToolKit.setHasInspection(true);
            updatedToolKit.setComments("kit update comments");
            updatedToolKit.setIsCalibrated(true);
            updatedToolKit.setInspection(inspectionEntity2);

            InspectionRequestDTO updateDto = new InspectionRequestDTO();
            updateDto.setInspectionType(InspectionType.CALIBRATION);
            updateDto.setInspectionPassed(true);
            updateDto.setComments("Change to Calibration inspection");
            updateDto.setToolId(null);
            updateDto.setToolKitId(2L);

            InspectionResponseDTO updatedResponseDTO = new InspectionResponseDTO();
            updatedResponseDTO.setId(1L);
            updatedResponseDTO.setInspectionType(InspectionType.CALIBRATION);
            updatedResponseDTO.setInspectionPassed(true);
            updatedResponseDTO.setComments("Change to Calibration inspection");
            updatedResponseDTO.setToolId(2L);
            updatedResponseDTO.setToolKitId(2L);
            updatedResponseDTO.setEquipmentItemId("Kit002");

            when(inspectionRepository.findById(id)).thenReturn(Optional.of(inspectionEntity2));
            when(toolKitRepository.findById(2L)).thenReturn(Optional.of(toolKit2));
            when(toolKitRepository.save(toolKit2)).thenReturn(updatedToolKit);
            when(inspectionRepository.save(inspectionEntity2)).thenReturn(inspectionEntity2);
            when(inspectionDTOMapper.mapToDto(inspectionEntity2)).thenReturn(updatedResponseDTO);

            // Act
            InspectionResponseDTO result = inspectionService.updateInspection(id, updateDto);

            //Assert
            assertEquals(updatedResponseDTO, result);
            assertEquals(2L, result.getToolKitId());
            assertTrue(toolKit2.getHasInspection());
            assertTrue(toolKit2.getIsCalibrated());
            verify(inspectionRepository, times(1)).findById(id);
            verify(toolKitRepository, times(1)).findById(2L);
            verify(inspectionRepository, times(1)).save(inspectionEntity2);
            verify(inspectionDTOMapper, times(1)).mapToDto(inspectionEntity2);
        }
    }

    @Nested
    @DisplayName("4. Delete Testing")
    class DeleteFunctionsTesting {
        @Test
        @DisplayName("Delete Tool Inspection, Working")
        void deleteToolInspection_shouldDelete_whenInspectionExists() {
            // Arrange
            Long id = 1L;

            ToolEntity updatedTool = new ToolEntity();
            updatedTool.setId(1L);
            updatedTool.setInspection(null);
            updatedTool.setHasInspection(false);
            updatedTool.setIsCalibrated(false);

            when(toolRepository.save(tool1)).thenReturn(updatedTool);
            when(inspectionRepository.findById(1L)).thenReturn(Optional.of(inspectionEntity1));

            // Act
            inspectionService.deleteInspection(id);

            // Assert
            assertNull(tool1.getInspection());
            assertFalse(tool1.getHasInspection());
            assertFalse(tool1.getIsCalibrated());
            verify(toolRepository, times(1)).save(tool1);
            verify(inspectionRepository, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("Delete Tool Kit Inspection, Working")
        void deleteToolKitInspection_shouldDelete_whenInspectionExists() {
            // Arrange
            Long id = 2L;

            ToolKitEntity updatedKitTool = new ToolKitEntity();
            updatedKitTool.setId(1L);
            updatedKitTool.setInspection(null);
            updatedKitTool.setHasInspection(false);
            updatedKitTool.setIsCalibrated(false);

            when(toolKitRepository.save(toolKit1)).thenReturn(updatedKitTool);
            when(inspectionRepository.findById(id)).thenReturn(Optional.of(inspectionEntity2));

            // Act
            inspectionService.deleteInspection(id);

            // Assert
            assertNull(toolKit1.getInspection());
            assertFalse(toolKit1.getHasInspection());
            assertFalse(toolKit1.getIsCalibrated());
            verify(toolKitRepository, times(1)).save(toolKit1);
            verify(inspectionRepository, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("Delete Inspection, Inspection Id Not Found / Throw Exception")
        void deleteAircraft_shouldThrow_whenAircraftDoesNotExist() {
            // Arrange
            Long id = 999L;
            when(inspectionRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                    () -> inspectionService.deleteInspection(id));
            assertTrue(ex.getMessage().contains("Inspection 999 not found"));
        }
    }

    @Nested
    @DisplayName("5. Complete Inspection Testing")
    class CompleteInspectionTesting {
        @Test
        @DisplayName("Complete Inspection on Tool TEST001, Working")
        void completeInspection_shouldReturnUpdatedToolInspection() {
            // Arrange
            tool1.setInspection(inspectionEntity1);

            completeInspectionDTO.setEquipmentItemId("TEST001");
            completeInspectionDTO.setInspectionDate(LocalDateTime.of(2011, 11, 11, 11, 11));
            completeInspectionDTO.setInspectionPassed(true);
            completeInspectionDTO.setComments("Performed Tool inspection");
            completeInspectionDTO.setNextDueDate(LocalDateTime.of(2028, 8, 8, 8, 8));

            InspectionRequestDTO updateDto = new InspectionRequestDTO();
            updateDto.setInspectionDate(LocalDateTime.of(2011, 11, 11, 11, 11));
            updateDto.setInspectionPassed(true);
            updateDto.setComments("Performed Tool inspection");
            updateDto.setNextDueDate(LocalDateTime.of(2028, 8, 8, 8, 8));

            InspectionResponseDTO updatedResponseDTO = new InspectionResponseDTO();
            updatedResponseDTO.setId(1L);
            updatedResponseDTO.setInspectionDate(LocalDateTime.of(2011, 11, 11, 11, 11));
            updatedResponseDTO.setInspectionType(InspectionType.CALIBRATION);
            updatedResponseDTO.setInspectionPassed(true);
            updatedResponseDTO.setComments("Performed Tool inspection");
            updatedResponseDTO.setNextDueDate(LocalDateTime.of(2028, 8, 8, 8, 8));
            updatedResponseDTO.setInspectionInterval(12);
            updatedResponseDTO.setToolId(1L);
            updatedResponseDTO.setToolKitId(null);
            updatedResponseDTO.setEquipmentItemId("TEST001");

            when(toolRepository.findByItemId(completeInspectionDTO.getEquipmentItemId())).thenReturn(Optional.of(tool1));
            when(inspectionRepository.findById(tool1.getInspection().getId())).thenReturn(Optional.of(inspectionEntity1));
            when(inspectionRepository.save(inspectionEntity1)).thenReturn(inspectionEntity1);
            when(inspectionDTOMapper.mapToDto(inspectionEntity1)).thenReturn(updatedResponseDTO);

            // Act
            InspectionResponseDTO result = inspectionService.completeInspection(completeInspectionDTO);

            //Assert
            assertEquals(updatedResponseDTO, result);
            assertEquals(1L, result.getToolId());
            verify(inspectionRepository, times(1)).findById(tool1.getInspection().getId());
            verify(inspectionRepository, times(1)).save(inspectionEntity1);
            verify(inspectionDTOMapper, times(1)).mapToDto(inspectionEntity1);
        }

        @Test
        @DisplayName("Complete Inspection on ToolKit Kit001, Working")
        void completeInspection_shouldReturnUpdatedToolKitInspection() {
            // Arrange
            toolKit1.setInspection(inspectionEntity2);

            completeInspectionDTO.setEquipmentItemId("Kit001");
            completeInspectionDTO.setInspectionDate(LocalDateTime.of(2001, 10, 10, 10, 10));
            completeInspectionDTO.setInspectionPassed(true);
            completeInspectionDTO.setComments("Performed Kit Tool inspection");
            completeInspectionDTO.setNextDueDate(LocalDateTime.of(2002, 8, 8, 8, 8));

            InspectionRequestDTO updateDto = new InspectionRequestDTO();
            updateDto.setInspectionDate(LocalDateTime.of(2001, 10, 10, 10, 10));
            updateDto.setInspectionPassed(true);
            updateDto.setComments("Performed Kit Tool inspection");
            updateDto.setNextDueDate(LocalDateTime.of(2002, 8, 8, 8, 8));

            InspectionResponseDTO updatedResponseDTO = new InspectionResponseDTO();
            updatedResponseDTO.setId(1L);
            updatedResponseDTO.setInspectionDate(LocalDateTime.of(2001, 10, 10, 10, 10));
            updatedResponseDTO.setInspectionType(InspectionType.CALIBRATION);
            updatedResponseDTO.setInspectionPassed(true);
            updatedResponseDTO.setComments("Performed Kit Tool inspection");
            updatedResponseDTO.setNextDueDate(LocalDateTime.of(2002, 8, 8, 8, 8));
            updatedResponseDTO.setInspectionInterval(12);
            updatedResponseDTO.setToolId(null);
            updatedResponseDTO.setToolKitId(1L);
            updatedResponseDTO.setEquipmentItemId("Kit001");

            when(toolKitRepository.findByItemId(completeInspectionDTO.getEquipmentItemId())).thenReturn(Optional.of(toolKit1));
            when(inspectionRepository.findById(toolKit1.getInspection().getId())).thenReturn(Optional.of(inspectionEntity2));
            when(inspectionRepository.save(inspectionEntity2)).thenReturn(inspectionEntity2);
            when(inspectionDTOMapper.mapToDto(inspectionEntity2)).thenReturn(updatedResponseDTO);

            // Act
            InspectionResponseDTO result = inspectionService.completeInspection(completeInspectionDTO);

            //Assert
            assertEquals(updatedResponseDTO, result);
            assertEquals(1L, result.getToolKitId());
            verify(inspectionRepository, times(1)).findById(toolKit1.getInspection().getId());
            verify(inspectionRepository, times(1)).save(inspectionEntity2);
            verify(inspectionDTOMapper, times(1)).mapToDto(inspectionEntity2);
        }

        @Test
        @DisplayName("Complete Inspection and Item Id Not Found / Throw Exception")
        void completeInspection_ItemIdNotFound_ThrowsException() {
            // Arrange
            completeInspectionDTO.setEquipmentItemId("FindMe");

            when(toolRepository.findByItemId(completeInspectionDTO.getEquipmentItemId())).thenReturn(Optional.empty());
            when(toolKitRepository.findByItemId(completeInspectionDTO.getEquipmentItemId())).thenReturn(Optional.empty());

            //Act & Assert
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> inspectionService.completeInspection(completeInspectionDTO));
            assertTrue(ex.getMessage().contains("No tool or toolkit found with itemId: FindMe"));
        }
    }

    @Nested
    @DisplayName("6. Find By Date Functions Testing")
    class FindByDateFunctionsTesting {
        @Test
        @DisplayName("Find all inspections with Date Before, Working")
        void findByInspectionDateBefore_shouldReturnList() {
            // Arrange
            LocalDateTime date = LocalDateTime.now();

            InspectionEntity inspectionEntity2 = new InspectionEntity();
            inspectionEntity2.setInspectionDate(LocalDateTime.of(2020, 10, 20, 10, 20));

            InspectionResponseDTO inspectionResponseDTO2 = new InspectionResponseDTO();
            inspectionResponseDTO2.setInspectionDate(LocalDateTime.of(2020, 10, 20, 10, 20));

            when(inspectionRepository.findByInspectionDateAfter(date)).thenReturn(Arrays.asList(inspectionEntity1, inspectionEntity2));
            when(inspectionDTOMapper.mapToDto(List.of(inspectionEntity1, inspectionEntity2))).thenReturn(List.of(inspectionResponseDTO1, inspectionResponseDTO2));

            // Act
            List<InspectionResponseDTO> result = inspectionService.findByInspectionDateAfter(date);

            // Assert
            assertEquals(2, result.size());
            assertTrue(result.get(0).getInspectionDate().isBefore(date));
            assertTrue(result.get(1).getInspectionDate().isBefore(date));
            verify(inspectionRepository, times(1)).findByInspectionDateAfter(date);
            verify(inspectionDTOMapper, times(1)).mapToDto(List.of(inspectionEntity1, inspectionEntity2));
        }

        @Test
        @DisplayName("Find all inspections with Date After, Working")
        void findByInspectionDateAfter_shouldReturnList() {
            // Arrange
            LocalDateTime date = LocalDateTime.of(2020, 10, 20, 10, 20);

            InspectionEntity inspectionEntity3 = new InspectionEntity();
            inspectionEntity3.setInspectionDate(LocalDateTime.of(2029, 10, 20, 10, 20));

            InspectionResponseDTO inspectionResponseDTO3 = new InspectionResponseDTO();
            inspectionResponseDTO3.setInspectionDate(LocalDateTime.of(2029, 10, 20, 10, 20));

            when(inspectionRepository.findByInspectionDateBefore(date)).thenReturn(Arrays.asList(inspectionEntity2, inspectionEntity3));
            when(inspectionDTOMapper.mapToDto(List.of(inspectionEntity2, inspectionEntity3))).thenReturn(List.of(inspectionResponseDTO2, inspectionResponseDTO3));

            // Act
            List<InspectionResponseDTO> result = inspectionService.findByInspectionDateBefore(date);

            // Assert
            assertEquals(2, result.size());
            assertTrue(result.get(0).getInspectionDate().isAfter(date));
            assertTrue(result.get(1).getInspectionDate().isAfter(date));
            verify(inspectionRepository, times(1)).findByInspectionDateBefore(date);
            verify(inspectionDTOMapper, times(1)).mapToDto(List.of(inspectionEntity2, inspectionEntity3));
        }

        @Test
        @DisplayName("Find all inspections that go due before Date, Working")
        void findOverdueByNextDueDateBefore_shouldReturnList() {
            // Arrange
            LocalDateTime date = LocalDateTime.of(2030, 10, 10, 10, 10);

            InspectionEntity inspectionEntity2 = new InspectionEntity();
            inspectionEntity2.setNextDueDate(LocalDateTime.of(2025, 10, 10, 10, 10));

            InspectionResponseDTO inspectionResponseDTO2 = new InspectionResponseDTO();
            inspectionResponseDTO2.setNextDueDate(LocalDateTime.of(2025, 10, 10, 10, 10));

            when(inspectionRepository.findOverdueByNextDueDateBefore(date)).thenReturn(Arrays.asList(inspectionEntity1, inspectionEntity2));
            when(inspectionDTOMapper.mapToDto(List.of(inspectionEntity1, inspectionEntity2))).thenReturn(List.of(inspectionResponseDTO1, inspectionResponseDTO2));

            // Act
            List<InspectionResponseDTO> result = inspectionService.findOverdueByNextDueDateBefore(date);

            // Assert
            assertEquals(2, result.size());
            assertTrue(result.get(0).getNextDueDate().isBefore(date));
            assertTrue(result.get(1).getNextDueDate().isBefore(date));
            verify(inspectionRepository, times(1)).findOverdueByNextDueDateBefore(date);
            verify(inspectionDTOMapper, times(1)).mapToDto(List.of(inspectionEntity1, inspectionEntity2));
        }
    }
}