package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.aircraft.AircraftRequestDTO;
import nl.novi.deepwrench42.dtos.aircraft.AircraftResponseDTO;
import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;
import nl.novi.deepwrench42.entities.AircraftEntity;
import nl.novi.deepwrench42.entities.AircraftTypeEntity;
import nl.novi.deepwrench42.entities.EngineTypeEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.AircraftDTOMapper;
import nl.novi.deepwrench42.repository.AircraftRepository;
import nl.novi.deepwrench42.repository.AircraftTypeRepository;
import nl.novi.deepwrench42.repository.EngineTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AircraftServiceTest {

    @Mock
    private AircraftRepository aircraftRepository;
    @Mock
    private AircraftTypeRepository aircraftTypeRepository;
    @Mock
    private EngineTypeRepository engineTypeRepository;
    @Mock
    private AircraftDTOMapper aircraftDTOMapper;

    @InjectMocks
    AircraftService aircraftService;

    private AircraftTypeEntity aircraftType1;
    private AircraftTypeEntity aircraftType2;
    private EngineTypeEntity engineType1;
    private EngineTypeEntity engineType2;

    private AircraftTypeResponseDTO aircraftTypeResponseDTO1;
    private AircraftTypeResponseDTO aircraftTypeResponseDTO2;
    private EngineTypeResponseDTO engineTypeResponseDTO1;
    private EngineTypeResponseDTO engineTypeResponseDTO2;
    private AircraftEntity aircraftEntity1;
    private AircraftRequestDTO aircraftRequestDTO1;
    private AircraftResponseDTO aircraftResponseDTO1;


    @BeforeEach
    void setUp() {
        aircraftType1 = new AircraftTypeEntity();
        aircraftType1.setId(1L);
        aircraftType1.setManufacturer("Airbus");
        aircraftType1.setMainType("A330");
        aircraftType1.setSubType("A330-300");

        aircraftType2 = new AircraftTypeEntity();
        aircraftType2.setId(2L);
        aircraftType2.setManufacturer("Boeing");
        aircraftType2.setMainType("B787");
        aircraftType2.setSubType("B787-800");

        engineType1 = new EngineTypeEntity();
        engineType1.setId(1L);
        engineType1.setManufacturer("Pratt & Whitney");
        engineType1.setMainType("PW4000");
        engineType1.setSubType("PW4168");

        engineType2 = new EngineTypeEntity();
        engineType2.setId(2L);
        engineType2.setManufacturer("General Electric");
        engineType2.setMainType("GenX");
        engineType2.setSubType("GenX-1A");

        aircraftTypeResponseDTO1 = new AircraftTypeResponseDTO();
        aircraftTypeResponseDTO1.setId(1L);
        aircraftTypeResponseDTO1.setManufacturer("Airbus");
        aircraftTypeResponseDTO1.setMainType("A330");
        aircraftTypeResponseDTO1.setSubType("A330-300");

        aircraftTypeResponseDTO2 = new AircraftTypeResponseDTO();
        aircraftTypeResponseDTO2.setId(2L);
        aircraftTypeResponseDTO2.setManufacturer("Boeing");
        aircraftTypeResponseDTO2.setMainType("B787");
        aircraftTypeResponseDTO2.setSubType("B787-800");

        engineTypeResponseDTO1 = new EngineTypeResponseDTO();
        engineTypeResponseDTO1.setId(1L);
        engineTypeResponseDTO1.setManufacturer("Pratt & Whitney");
        engineTypeResponseDTO1.setMainType("PW4000");
        engineTypeResponseDTO1.setSubType("PW4168");

        engineTypeResponseDTO2 = new EngineTypeResponseDTO();
        engineTypeResponseDTO2.setId(2L);
        engineTypeResponseDTO2.setManufacturer("General Electric");
        engineTypeResponseDTO2.setMainType("GenX");
        engineTypeResponseDTO2.setSubType("GenX-1A");

        aircraftEntity1 = new AircraftEntity();
        aircraftEntity1.setId(1L);
        aircraftEntity1.setShipNumber("ship1");
        aircraftEntity1.setRegistration("registration1");
        aircraftEntity1.setAircraftType(aircraftType1);
        aircraftEntity1.setEngineType(engineType1);

        aircraftRequestDTO1 = new AircraftRequestDTO();
        aircraftRequestDTO1.setShipNumber("ship1");
        aircraftRequestDTO1.setRegistration("registration1");
        aircraftRequestDTO1.setAircraftTypeId(1L);
        aircraftRequestDTO1.setEngineTypeId(1L);

        aircraftResponseDTO1 = new AircraftResponseDTO();
        aircraftResponseDTO1.setId(1L);
        aircraftResponseDTO1.setShipNumber("ship1");
        aircraftResponseDTO1.setRegistration("registration1");
        aircraftResponseDTO1.setAircraftType(aircraftTypeResponseDTO1);
        aircraftResponseDTO1.setEngineType(engineTypeResponseDTO1);
    }

    @Nested
    @DisplayName("1. Get Testing")
    class GetFunctionsTesting {
        @Test
        @DisplayName("Find all aircraft, Working")
        void findAllAircraft_shouldReturnList() {
            // Arrange
            AircraftEntity aircraftEntity2 = new AircraftEntity();
            aircraftEntity2.setShipNumber("ship2");
            aircraftEntity2.setRegistration("registration2");
            aircraftEntity2.setAircraftType(aircraftType2);
            aircraftEntity2.setEngineType(engineType2);
            AircraftResponseDTO aircraftResponseDTO2 = new AircraftResponseDTO();
            aircraftResponseDTO2.setShipNumber("ship2");
            aircraftResponseDTO2.setRegistration("registration2");
            aircraftResponseDTO2.setAircraftType(aircraftTypeResponseDTO2);
            aircraftResponseDTO2.setEngineType(engineTypeResponseDTO2);

            when(aircraftRepository.findAll()).thenReturn(Arrays.asList(aircraftEntity1, aircraftEntity2));
            when(aircraftDTOMapper.mapToDto(List.of(aircraftEntity1, aircraftEntity2))).thenReturn(List.of(aircraftResponseDTO1, aircraftResponseDTO2));

            // Act
            List<AircraftResponseDTO> result = aircraftService.findAllAircraft();

            // Assert
            assertEquals(2, result.size());
            assertEquals(aircraftResponseDTO1, result.get(0));
            assertEquals(aircraftResponseDTO2, result.get(1));
            verify(aircraftRepository, times(1)).findAll();
            verify(aircraftDTOMapper, times(1)).mapToDto(List.of(aircraftEntity1, aircraftEntity2));
        }

        @Test
        @DisplayName("Find aircraft id 1, Working")
        void findAircraftById_shouldReturnAircraftResponseDTO_whenAircraftExists() {
            // Arrange
            when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraftEntity1));
            when(aircraftDTOMapper.mapToDto(aircraftEntity1)).thenReturn(aircraftResponseDTO1);

            // Act
            AircraftResponseDTO result = aircraftService.findAircraftById(1L);

            // Assert
            assertEquals("ship1", result.getShipNumber());
            assertEquals("registration1", result.getRegistration());
            assertEquals(aircraftTypeResponseDTO1, result.getAircraftType());
            assertEquals(engineTypeResponseDTO1, result.getEngineType());
        }

        @Test
        @DisplayName("Find aircraft id 999, Aircraft Not Found / Throw Exception")
        void findAircraftById_shouldThrowException_whenAircraftDoesNotExist() {
            // Arrange
            Long id = 999L;
            when(aircraftRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                    () -> aircraftService.findAircraftById(id));
            assertTrue(ex.getMessage().contains("Aircraft 999 not found"));
        }
    }

    @Nested
    @DisplayName("2. Create Testing")
    class CreateFunctionsTesting {
        @Test
        @DisplayName("Create new aircraft, Working")
        void createAircraft_shouldReturnCreatedAircraft() {
            // Arrange
            when(aircraftDTOMapper.mapToEntity(aircraftRequestDTO1)).thenReturn(aircraftEntity1);
            when(aircraftTypeRepository.findById(1L)).thenReturn(Optional.of(aircraftType1));
            when(engineTypeRepository.findById(1L)).thenReturn(Optional.of(engineType1));
            when(aircraftRepository.save(aircraftEntity1)).thenReturn(aircraftEntity1);
            when(aircraftDTOMapper.mapToDto(aircraftEntity1)).thenReturn(aircraftResponseDTO1);

            // Act
            AircraftResponseDTO result = aircraftService.createAircraft(aircraftRequestDTO1);

            //Assert
            assertEquals(aircraftResponseDTO1, result);
            assertEquals(aircraftTypeResponseDTO1, result.getAircraftType());
            assertEquals(engineTypeResponseDTO1, result.getEngineType());
            verify(aircraftDTOMapper, times(1)).mapToEntity(aircraftRequestDTO1);
            verify(aircraftRepository, times(1)).save(aircraftEntity1);
            verify(aircraftDTOMapper, times(1)).mapToDto(aircraftEntity1);
        }

        @Test
        @DisplayName("Create new aircraft, Aircraft Type Not Found / Throw Exception")
        void createAircraft_shouldThrow_AircraftType_NotFoundException() {
            AircraftRequestDTO requestDto = new AircraftRequestDTO();
            Long aircraftTypeId = 999L;
            requestDto.setShipNumber("7777");
            requestDto.setRegistration("CREATE3");
            requestDto.setAircraftTypeId(aircraftTypeId);
            requestDto.setEngineTypeId(1L);
            when(aircraftTypeRepository.findById(aircraftTypeId)).thenReturn(Optional.empty());

            RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                    () -> aircraftService.createAircraft(requestDto));
            assertTrue(ex.getMessage().contains("Aircraft Type 999 not found"));
        }

        @Test
        @DisplayName("Create new aircraft, Engine Type Not Found / Throw Exception")
        void createAircraft_shouldThrow_EngineType_NotFoundException() {
            AircraftRequestDTO requestDto = new AircraftRequestDTO();
            Long engineTypeId = 999L;
            requestDto.setShipNumber("8888");
            requestDto.setRegistration("CREATE4");
            requestDto.setAircraftTypeId(1L);
            requestDto.setEngineTypeId(engineTypeId);
            when(aircraftDTOMapper.mapToEntity(requestDto)).thenReturn(aircraftEntity1);
            when(aircraftTypeRepository.findById(1L)).thenReturn(Optional.of(aircraftType1));
            when(engineTypeRepository.findById(999L)).thenReturn(Optional.empty());


            RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                    () -> aircraftService.createAircraft(requestDto));
            assertTrue(ex.getMessage().contains("Engine Type 999 not found"));
        }
    }

    @Nested
    @DisplayName("3. Update Testing")
    class UpdateFunctionsTesting {
        @Test
        @DisplayName("Update aircraft, Working")
        void updateAircraft_shouldReturnUpdatedAircraft() {
            // Arrange
            Long id = 1L;

            AircraftRequestDTO updateDto = new AircraftRequestDTO();
            updateDto.setShipNumber("3333");
            updateDto.setRegistration("UPDATE");
            updateDto.setAircraftTypeId(2L);
            updateDto.setEngineTypeId(2L);

            AircraftResponseDTO updatedResponseDTO = new AircraftResponseDTO();
            updatedResponseDTO.setId(1L);
            updatedResponseDTO.setShipNumber("3333");
            updatedResponseDTO.setRegistration("UPDATE");
            updatedResponseDTO.setAircraftType(aircraftTypeResponseDTO2);
            updatedResponseDTO.setEngineType(engineTypeResponseDTO2);

            when(aircraftRepository.findById(id)).thenReturn(Optional.of(aircraftEntity1));
            when(aircraftTypeRepository.findById(2L)).thenReturn(Optional.of(aircraftType2));
            when(engineTypeRepository.findById(2L)).thenReturn(Optional.of(engineType2));

            when(aircraftRepository.save(aircraftEntity1)).thenReturn(aircraftEntity1);
            when(aircraftDTOMapper.mapToDto(aircraftEntity1)).thenReturn(updatedResponseDTO);

            // Act
            AircraftResponseDTO result = aircraftService.updateAircraft(id, updateDto);

            //Assert
            assertEquals(updatedResponseDTO, result);
            assertEquals("3333", result.getShipNumber());
            assertEquals("UPDATE", result.getRegistration());
            assertEquals(aircraftTypeResponseDTO2, result.getAircraftType());
            assertEquals(engineTypeResponseDTO2, result.getEngineType());

            verify(aircraftRepository, times(1)).findById(id);
            verify(aircraftTypeRepository, times(1)).findById(2L);
            verify(engineTypeRepository, times(1)).findById(2L);
            verify(aircraftRepository, times(1)).save(aircraftEntity1);
            verify(aircraftDTOMapper, times(1)).mapToDto(aircraftEntity1);
        }

        @Test
        @DisplayName("Update aircraft, Working, Without Type Changes")
        void updateAircraft_shouldReturnUpdatedAircraft_withoutEngineOrAircraftTypeChange() {
            // Arrange
            Long id = 1L;

            AircraftRequestDTO updateDto = new AircraftRequestDTO();
            updateDto.setShipNumber("4444");
            updateDto.setRegistration("UPDATE1");
            updateDto.setAircraftTypeId(1L);
            updateDto.setEngineTypeId(1L);

            AircraftResponseDTO updatedResponseDTO = new AircraftResponseDTO();
            updatedResponseDTO.setId(1L);
            updatedResponseDTO.setShipNumber("4444");
            updatedResponseDTO.setRegistration("UPDATE1");
            updatedResponseDTO.setAircraftType(aircraftTypeResponseDTO1);
            updatedResponseDTO.setEngineType(engineTypeResponseDTO1);

            when(aircraftRepository.findById(id)).thenReturn(Optional.of(aircraftEntity1));

            when(aircraftRepository.save(aircraftEntity1)).thenReturn(aircraftEntity1);
            when(aircraftDTOMapper.mapToDto(aircraftEntity1)).thenReturn(updatedResponseDTO);

            // Act
            AircraftResponseDTO result = aircraftService.updateAircraft(id, updateDto);

            //Assert
            assertEquals(updatedResponseDTO, result);
            assertEquals("4444", result.getShipNumber());
            assertEquals("UPDATE1", result.getRegistration());
            assertEquals(aircraftTypeResponseDTO1, result.getAircraftType());
            assertEquals(engineTypeResponseDTO1, result.getEngineType());

            verify(aircraftTypeRepository, times(0)).findById(1L);
            verify(engineTypeRepository, times(0)).findById(1L);
            verify(aircraftRepository, times(1)).save(aircraftEntity1);
            verify(aircraftDTOMapper, times(1)).mapToDto(aircraftEntity1);
        }


        @Test
        @DisplayName("Update aircraft, Aircraft Not Found / Throw Exception")
        void updateAircraft_shouldThrow_whenAircraftDoesNotExist() {
            // Arrange
            Long id = 999L;

            AircraftRequestDTO updateDto = new AircraftRequestDTO();
            updateDto.setShipNumber("5555");
            updateDto.setRegistration("UPDATE2");
            updateDto.setAircraftTypeId(1L);
            updateDto.setEngineTypeId(1L);
            when(aircraftRepository.findById(id)).thenReturn(Optional.empty());

            //Act & Assert
            RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                    () -> aircraftService.updateAircraft(id, updateDto));
            assertTrue(ex.getMessage().contains("Aircraft 999 not found"));
        }

        @Test
        @DisplayName("Update aircraft, Aircraft Type Not Found / Throw Exception")
        void updateAircraft_shouldThrow_AircraftType_NotFoundException() {
            // Arrange
            Long id = 1L;
            Long aircraftTypeId = 999L;
            AircraftRequestDTO updateDto = new AircraftRequestDTO();
            updateDto.setShipNumber("6666");
            updateDto.setRegistration("UPDATE3");
            updateDto.setAircraftTypeId(aircraftTypeId);
            updateDto.setEngineTypeId(1L);
            when(aircraftRepository.findById(id)).thenReturn(Optional.of(aircraftEntity1));
            when(aircraftTypeRepository.findById(aircraftTypeId)).thenReturn(Optional.empty());

            //Act & Assert
            RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                    () -> aircraftService.updateAircraft(id, updateDto));
            assertTrue(ex.getMessage().contains("Aircraft Type 999 not found"));
        }

        @Test
        @DisplayName("Update aircraft, Engine Type Not Found / Throw Exception")
        void updateAircraft_shouldThrow_EngineType_NotFoundException() {
            // Arrange
            Long id = 1L;
            Long engineTypeId = 999L;
            AircraftRequestDTO updateDto = new AircraftRequestDTO();
            updateDto.setShipNumber("6666");
            updateDto.setRegistration("UPDATE3");
            updateDto.setAircraftTypeId(1L);
            updateDto.setEngineTypeId(engineTypeId);
            when(aircraftRepository.findById(id)).thenReturn(Optional.of(aircraftEntity1));
            when(engineTypeRepository.findById(engineTypeId)).thenReturn(Optional.empty());

            //Act & Assert
            RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                    () -> aircraftService.updateAircraft(id, updateDto));
            assertTrue(ex.getMessage().contains("Engine Type 999 not found"));
        }
    }

    @Nested
    @DisplayName("4. Delete Testing")
    class DeleteFunctionsTesting {
        @Test
        @DisplayName("Delete aircraft, Working")
        void deleteAircraft_shouldDelete_whenAircraftExists() {
            // Arrange
            Long id = 1L;
            when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraftEntity1));

            // Act
            aircraftService.deleteAircraft(id);

            // Assert
            verify(aircraftRepository, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("Delete aircraft, Aircraft Not Found / Throw Exception")
        void deleteAircraft_shouldThrow_whenAircraftDoesNotExist() {
            // Arrange
            Long id = 999L;
            when(aircraftRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                    () -> aircraftService.deleteAircraft(id));
            assertTrue(ex.getMessage().contains("Aircraft 999 not found"));
        }
    }
}