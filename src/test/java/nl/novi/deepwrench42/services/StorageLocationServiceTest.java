package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationRequestDTO;
import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationResponseDTO;
import nl.novi.deepwrench42.entities.StorageLocationEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.StorageLocationDTOMapper;
import nl.novi.deepwrench42.repository.StorageLocationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageLocationServiceTest {

    @Mock
    private StorageLocationRepository storageLocationRepository;

    @Mock
    private StorageLocationDTOMapper storageLocationDTOMapper;

    @InjectMocks
    private StorageLocationService storageLocationService;

    @Nested
    @DisplayName("1. Get Testing")
    class GetFunctionsTesting {
        @Test
        @DisplayName("Find all Storage Location, Working")
        void findAllStorageLocations_shouldReturnListOfStorageLocationResponseDTOs() {
            // Arrange
            StorageLocationEntity storageLocationEntity1 = new StorageLocationEntity();
            storageLocationEntity1.setLocation("test 1");
            storageLocationEntity1.setRack("1");
            storageLocationEntity1.setShelf("2");
            storageLocationEntity1.setComments("test 1 description");
            StorageLocationEntity storageLocationEntity2 = new StorageLocationEntity();
            storageLocationEntity2.setLocation("test 2");
            storageLocationEntity2.setRack("4");
            storageLocationEntity2.setShelf("5");
            storageLocationEntity2.setComments("test 2 description");
            StorageLocationResponseDTO storageLocationResponseDTO1 = new StorageLocationResponseDTO();
            storageLocationResponseDTO1.setLocation("test 1");
            storageLocationResponseDTO1.setRack("1");
            storageLocationResponseDTO1.setShelf("2");
            storageLocationResponseDTO1.setComments("test 1 description");
            StorageLocationResponseDTO storageLocationResponseDTO2 = new StorageLocationResponseDTO();
            storageLocationResponseDTO2.setLocation("test 2");
            storageLocationResponseDTO2.setRack("4");
            storageLocationResponseDTO2.setShelf("5");
            storageLocationResponseDTO2.setComments("test 2 description");

            when(storageLocationRepository.findAll()).thenReturn(Arrays.asList(storageLocationEntity1, storageLocationEntity2));
            when(storageLocationDTOMapper.mapToDto(List.of(storageLocationEntity1, storageLocationEntity2))).thenReturn(List.of(storageLocationResponseDTO1, storageLocationResponseDTO2));

            // Act
            List<StorageLocationResponseDTO> result = storageLocationService.findAllStorageLocations();

            // Assert
            assertEquals(2, result.size());
            assertEquals(storageLocationResponseDTO1, result.get(0));
            assertEquals(storageLocationResponseDTO2, result.get(1));
            verify(storageLocationRepository, times(1)).findAll();
            verify(storageLocationDTOMapper, times(1)).mapToDto(List.of(storageLocationEntity1, storageLocationEntity2));
        }

        @Test
        @DisplayName("Find Storage Location id 1, Working")
        void findStorageLocationById_shouldReturnStorageLocationResponseDTO_whenStorageLocationExists() {
            // Arrange
            Long id = 1L;
            StorageLocationResponseDTO storageLocationResponseDTO = new StorageLocationResponseDTO();
            storageLocationResponseDTO.setLocation("Updated Name");
            storageLocationResponseDTO.setRack("11");
            storageLocationResponseDTO.setShelf("22");
            storageLocationResponseDTO.setComments("Updated Description");

            StorageLocationEntity storageLocationEntity = new StorageLocationEntity();
            storageLocationEntity.setLocation("Old Name");
            storageLocationEntity.setRack("10");
            storageLocationEntity.setShelf("20");
            storageLocationEntity.setComments("Old Description");

            when(storageLocationRepository.findById(id)).thenReturn(Optional.of(storageLocationEntity));
            when(storageLocationDTOMapper.mapToDto(storageLocationEntity)).thenReturn(storageLocationResponseDTO);

            // Act
            StorageLocationResponseDTO result = storageLocationService.findStorageLocationById(id);

            // Assert
            assertEquals(storageLocationResponseDTO, result);
            verify(storageLocationRepository, times(1)).findById(id);
            verify(storageLocationDTOMapper, times(1)).mapToDto(storageLocationEntity);
        }

        @Test
        @DisplayName("Find Storage Location id 999, Storage Location Not Found / Throw Exception")
        void findStorageLocationById_shouldThrowEntityNotFoundException_whenStorageLocationDoesNotExist() {
            // Arrange
            Long id = 1L;

            when(storageLocationRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
                storageLocationService.findStorageLocationById(id);
            });
            assertEquals("Storage location " + id + " not found", exception.getMessage());
            verify(storageLocationRepository, times(1)).findById(id);
        }
    }

    @Nested
    @DisplayName("2. Create Testing")
    class CreateFunctionsTesting {
        @Test
        @DisplayName("Create new Storage Location, Working")
        void createStorageLocation_shouldReturnCreatedStorageLocationResponseDTO() {
            // Arrange

            StorageLocationResponseDTO storageLocationResponseDTO = new StorageLocationResponseDTO();
            storageLocationResponseDTO.setLocation("Updated Name");
            storageLocationResponseDTO.setRack("11");
            storageLocationResponseDTO.setShelf("22");
            storageLocationResponseDTO.setComments("Updated Description");

            StorageLocationRequestDTO storageLocationRequestDTO = new StorageLocationRequestDTO();
            storageLocationRequestDTO.setLocation("Updated Name");
            storageLocationRequestDTO.setRack("11");
            storageLocationRequestDTO.setShelf("22");
            storageLocationRequestDTO.setComments("Updated Description");

            StorageLocationEntity storageLocationEntity = new StorageLocationEntity();
            storageLocationEntity.setLocation("Old Name");
            storageLocationEntity.setRack("10");
            storageLocationEntity.setShelf("20");
            storageLocationEntity.setComments("Old Description");

            when(storageLocationDTOMapper.mapToEntity(storageLocationRequestDTO)).thenReturn(storageLocationEntity);
            when(storageLocationRepository.save(storageLocationEntity)).thenReturn(storageLocationEntity);
            when(storageLocationDTOMapper.mapToDto(storageLocationEntity)).thenReturn(storageLocationResponseDTO);

            // Act
            StorageLocationResponseDTO result = storageLocationService.createStorageLocation(storageLocationRequestDTO);

            // Assert
            assertEquals(storageLocationResponseDTO, result);
            verify(storageLocationDTOMapper, times(1)).mapToEntity(storageLocationRequestDTO);
            verify(storageLocationRepository, times(1)).save(storageLocationEntity);
            verify(storageLocationDTOMapper, times(1)).mapToDto(storageLocationEntity);
        }
    }

    @Nested
    @DisplayName("3. Update Testing")
    class UpdateFunctionsTesting {
        @Test
        @DisplayName("Update Storage Location id 1, Working")
        void updateStorageLocation_shouldReturnUpdatedStorageLocationResponseDTO_whenStorageLocationExists() {
            // Arrange
            Long id = 1L;
            StorageLocationResponseDTO storageLocationResponseDTO = new StorageLocationResponseDTO();
            storageLocationResponseDTO.setLocation("Updated Name");
            storageLocationResponseDTO.setRack("11");
            storageLocationResponseDTO.setShelf("22");
            storageLocationResponseDTO.setComments("Updated Description");

            StorageLocationRequestDTO storageLocationRequestDTO = new StorageLocationRequestDTO();
            storageLocationRequestDTO.setLocation("Updated Name");
            storageLocationRequestDTO.setRack("11");
            storageLocationRequestDTO.setShelf("22");
            storageLocationRequestDTO.setComments("Updated Description");

            StorageLocationEntity existingStorageLocationEntity = new StorageLocationEntity();
            existingStorageLocationEntity.setLocation("Old Name");
            existingStorageLocationEntity.setRack("10");
            existingStorageLocationEntity.setShelf("20");
            existingStorageLocationEntity.setComments("Old Description");

            when(storageLocationRepository.findById(id)).thenReturn(Optional.of(existingStorageLocationEntity));
            when(storageLocationRepository.save(existingStorageLocationEntity)).thenReturn(existingStorageLocationEntity);
            when(storageLocationDTOMapper.mapToDto(existingStorageLocationEntity)).thenReturn(storageLocationResponseDTO);

            // Act
            StorageLocationResponseDTO result = storageLocationService.updateStorageLocation(id, storageLocationRequestDTO);

            // Assert
            assertEquals(storageLocationResponseDTO, result);
            assertEquals("Updated Name", existingStorageLocationEntity.getLocation());
            assertEquals("11", existingStorageLocationEntity.getRack());
            assertEquals("22", existingStorageLocationEntity.getShelf());
            assertEquals("Updated Description", existingStorageLocationEntity.getComments());
            verify(storageLocationRepository, times(1)).findById(id);
            verify(storageLocationRepository, times(1)).save(existingStorageLocationEntity);
            verify(storageLocationDTOMapper, times(1)).mapToDto(existingStorageLocationEntity);
        }

        @Test
        @DisplayName("Update Storage Location Not Found / Throw Exception")
        void updateStorageLocation_shouldThrowEntityNotFoundException_whenStorageLocationDoesNotExist() {
            // Arrange
            Long id = 1L;
            StorageLocationRequestDTO storageLocationRequestDTO = new StorageLocationRequestDTO();
            storageLocationRequestDTO.setLocation("Updated Name");
            storageLocationRequestDTO.setRack("11");
            storageLocationRequestDTO.setShelf("22");
            storageLocationRequestDTO.setComments("Updated Description");

            when(storageLocationRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
                storageLocationService.updateStorageLocation(id, storageLocationRequestDTO);
            });
            assertEquals("Storage location " + id + " not found", exception.getMessage());
            verify(storageLocationRepository, times(1)).findById(id);
        }
    }

    @Nested
    @DisplayName("4. Delete Testing")
    class DeleteFunctionsTesting {
        @Test
        @DisplayName("Delete Storage Location, Working")
        void deleteStorageLocation_shouldCallDeleteById() {
            // Arrange
            Long id = 1L;
            StorageLocationEntity storageLocationEntity = new StorageLocationEntity();
            storageLocationEntity.setLocation("Old Name");
            storageLocationEntity.setRack("10");
            storageLocationEntity.setShelf("20");
            storageLocationEntity.setComments("Old Description");

            when(storageLocationRepository.findById(id)).thenReturn(Optional.of(storageLocationEntity));

            // Act
            storageLocationService.deleteStorageLocation(id);

            // Assert
            verify(storageLocationRepository, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("Delete Storage Location, Storage Location Id Not Found / Throw Exception")
        void deleteStorageLocation_shouldThrowEntityNotFoundException_whenStorageLocationDoesNotExist() {
            // Arrange
            Long id = 1L;

            // Act
            RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
                    storageLocationService.deleteStorageLocation(1L));
            // Assert
            assertEquals("Storage location 1 not found", exception.getMessage());
        }
    }
}

