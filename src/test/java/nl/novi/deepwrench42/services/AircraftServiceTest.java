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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AircraftServiceTest {

    private AircraftRepository aircraftRepository;
    private AircraftTypeRepository aircraftTypeRepository;
    private EngineTypeRepository engineTypeRepository;
    private AircraftDTOMapper aircraftDTOMapper;

    private AircraftService aircraftService;

    private AircraftEntity aircraftEntity;
    private AircraftRequestDTO requestDTO;
    private AircraftResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        aircraftDTOMapper = mock(AircraftDTOMapper.class);
        engineTypeRepository = mock(EngineTypeRepository.class);
        aircraftTypeRepository = mock(AircraftTypeRepository.class);
        aircraftRepository = mock(AircraftRepository.class);

        aircraftService = new AircraftService(
                aircraftRepository,
                aircraftTypeRepository,
                engineTypeRepository,
                aircraftDTOMapper
        );

        AircraftTypeEntity aircraftType = new AircraftTypeEntity();
        aircraftType.setId(1L);
        aircraftType.setManufacturer("Boeing");
        aircraftType.setMainType("737");
        aircraftType.setSubType("737-700");
        aircraftTypeRepository.save(aircraftType);

        AircraftTypeResponseDTO aircraftTypeResponse = new AircraftTypeResponseDTO();
        aircraftTypeResponse.setId(1L);
        aircraftTypeResponse.setManufacturer("Boeing");
        aircraftTypeResponse.setMainType("737");
        aircraftTypeResponse.setSubType("737-700");

        EngineTypeEntity engineType = new EngineTypeEntity();
        engineType.setId(1L);
        engineType.setManufacturer("CFM");
        engineType.setMainType("CFM56");
        engineType.setSubType("CFM56-7B24");
        engineTypeRepository.save(engineType);

        EngineTypeResponseDTO engineTypeResponse = new EngineTypeResponseDTO();
        engineTypeResponse.setId(1L);
        engineTypeResponse.setManufacturer("CFM");
        engineTypeResponse.setMainType("CFM56");
        engineTypeResponse.setSubType("CFM56-7B24");

        aircraftEntity = new AircraftEntity();
        aircraftEntity.setId(1L);
        aircraftEntity.setShipNumber("100");
        aircraftEntity.setRegistration("PH-TEST");
        aircraftEntity.setAircraftType(aircraftType);
        aircraftEntity.setEngineType(engineType);

        requestDTO = new AircraftRequestDTO();
        requestDTO.setShipNumber("110");
        requestDTO.setRegistration("PH-REQ");
        requestDTO.setAircraftTypeId(1L);
        requestDTO.setEngineTypeId(1L);

        responseDTO = new AircraftResponseDTO();
        responseDTO.setShipNumber("111");
        responseDTO.setRegistration("PH-RES");
        responseDTO.setAircraftType(aircraftTypeResponse);
        responseDTO.setEngineType(engineTypeResponse);
    }

    @Test
    void findAllAircraft_shouldReturnListOfDTOs() {
        when(aircraftRepository.findAll()).thenReturn(List.of(aircraftEntity));
        when(aircraftDTOMapper.mapToDto(List.of(aircraftEntity))).thenReturn(List.of(responseDTO));

        List<AircraftResponseDTO> result = aircraftService.findAllAircraft();

        assertEquals(1, List.of(responseDTO).size());
        assertEquals("PH-RES", result.get(0).getRegistration());
        verify(aircraftRepository, times(1)).findAll();
        verify(aircraftDTOMapper, times(1)).mapToDto(List.of(aircraftEntity));
    }

    @Test
    void findAircraftById_shouldReturnAircraftResponseDTO() {
        responseDTO = new AircraftResponseDTO();
        responseDTO.setShipNumber("111");
        responseDTO.setRegistration("PH-RES");

        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraftEntity));
        when(aircraftDTOMapper.mapToDto(aircraftEntity)).thenReturn(responseDTO);

        AircraftResponseDTO result = aircraftService.findAircraftById(1L);

        assertEquals("111", result.getShipNumber());
        assertEquals("PH-RES", result.getRegistration());
        verify(aircraftRepository).findById(1L);
    }

    @Test
    void createAircraft_shouldReturnAircraftResponseDTO() {
        EngineTypeEntity engineType = new EngineTypeEntity();
        AircraftTypeEntity aircraftType = new AircraftTypeEntity();

        when(aircraftDTOMapper.mapToEntity(requestDTO)).thenReturn(aircraftEntity);
        when(engineTypeRepository.findById(1L)).thenReturn(Optional.of(engineType));
        when(aircraftTypeRepository.findById(1L)).thenReturn(Optional.of(aircraftType));
        when(aircraftRepository.save(aircraftEntity)).thenReturn(aircraftEntity);
        when(aircraftDTOMapper.mapToDto(aircraftEntity)).thenReturn(responseDTO);

        AircraftResponseDTO result = aircraftService.createAircraft(requestDTO);

        assertEquals("111", result.getShipNumber());
        assertEquals("PH-RES", result.getRegistration());
        verify(aircraftRepository).save(aircraftEntity);
    }


    @Test
    void createAircraftWithoutEngineTypeId_shouldReturnAircraftResponseDTO() {

        AircraftTypeEntity aircraftType = new AircraftTypeEntity();

        when(aircraftTypeRepository.findById(1L)).thenReturn(Optional.of(aircraftType));
        when(aircraftDTOMapper.mapToEntity(requestDTO)).thenReturn(aircraftEntity);
        when(aircraftRepository.save(aircraftEntity)).thenReturn(aircraftEntity);
        when(aircraftDTOMapper.mapToDto(aircraftEntity)).thenReturn(responseDTO);

        AircraftResponseDTO result = aircraftService.createAircraft(requestDTO);

        /*assertEquals("PH-RES", result.getRegistration());
        verify(aircraftRepository).save(aircraftEntity);*/

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
                engineTypeRepository.findById(1L));

        assertEquals("Engine Type 1 not found", exception.getMessage());
    }


   /* @Test
    void updateAircraft_shouldReturnUpdatedDTO() {
        EngineTypeEntity engineType = new EngineTypeEntity();
        AircraftTypeEntity aircraftType = new AircraftTypeEntity();

        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraftEntity));
        when(engineTypeRepository.findById(1L)).thenReturn(Optional.of(engineType));
        when(aircraftTypeRepository.findById(1L)).thenReturn(Optional.of(aircraftType));
        when(aircraftRepository.save(aircraftEntity)).thenReturn(aircraftEntity);
        when(aircraftDTOMapper.mapToDto(aircraftEntity)).thenReturn(responseDTO);

        AircraftResponseDTO result = aircraftService.updateAircraft(1L, requestDTO);

        assertEquals("Test Aircraft", result.getTitle());
        verify(aircraftRepository).save(aircraftEntity);
    }*/


 /*   @Test
    void deleteAircraft_shouldDeleteWhenNoStock() {
        aircraftEntity.setStockItems(new ArrayList<>());

        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraftEntity));

        aircraftService.deleteAircraft(1L);

        verify(aircraftRepository).deleteById(1L);
    }*/


/*    @Test
    void deleteAircraft_shouldNotDeleteWhenStockExists() {
        aircraftEntity.setStockItems(List.of(new StockEntity()));

        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraftEntity));

        aircraftService.deleteAircraft(1L);

        verify(aircraftRepository, never()).deleteById(1L);
    }*/


/*    @Test
    void linkArtist_shouldAddArtistToAircraft() {
        ArtistEntity artist = new ArtistEntity();
        artist.setAircraft(new HashSet<>());

        aircraftEntity.setArtists(new HashSet<>());

        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraftEntity));
        when(artistRepository.findById(2L)).thenReturn(Optional.of(artist));
        when(aircraftRepository.save(aircraftEntity)).thenReturn(aircraftEntity);

        aircraftService.linkArtist(1L, 2L);

        assertTrue(aircraftEntity.getArtists().contains(artist));
        assertTrue(artist.getAircraft().contains(aircraftEntity));
    }*/


 /*   @Test
    void unlinkArtist_shouldRemoveArtistFromAircraft() {
        ArtistEntity artist = new ArtistEntity();
        artist.setAircraft(new HashSet<>(Set.of(aircraftEntity)));

        aircraftEntity.setArtists(new HashSet<>(Set.of(artist)));

        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraftEntity));
        when(artistRepository.findById(2L)).thenReturn(Optional.of(artist));
        when(aircraftRepository.save(aircraftEntity)).thenReturn(aircraftEntity);

        aircraftService.unlinkArtist(1L, 2L);

        assertFalse(aircraftEntity.getArtists().contains(artist));
        assertFalse(artist.getAircraft().contains(aircraftEntity));
    }*/


/*    @Test
    void getAircraftWithStock_shouldReturnAircraftWithStock() {
        when(aircraftRepository.findByStockItemsNotEmpty()).thenReturn(List.of(aircraftEntity));
        when(aircraftDTOMapper.mapToDto(List.of(aircraftEntity))).thenReturn(List.of(responseDTO));

        List<AircraftResponseDTO> result = aircraftService.getAircraftWithStock(true);

        assertEquals(1, result.size());
        verify(aircraftRepository).findByStockItemsNotEmpty();
    }*/


/*    @Test
    void getAircraftWithStock_shouldReturnAircraftWithoutStock() {
        when(aircraftRepository.findByStockItemsEmpty()).thenReturn(List.of(aircraftEntity));
        when(aircraftDTOMapper.mapToDto(List.of(aircraftEntity))).thenReturn(List.of(responseDTO));

        List<AircraftResponseDTO> result = aircraftService.getAircraftWithStock(false);

        assertEquals(1, result.size());
        verify(aircraftRepository).findByStockItemsEmpty();
    }*/


    @Test
    void getAircraftEntity_shouldThrowException_WhenNotFound() {
        when(aircraftRepository.findById(100L)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
                aircraftService.findAircraftById(100L));

        assertEquals("Aircraft 100 not found", exception.getMessage());
    }


/*    @Test
    void createAircraft_shouldThrowException_WhenEngineTypeNotFound() {
        when(engineTypeRepository.findById(1L)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
                aircraftService.createAircraft(requestDTO));

        assertTrue(exception.getMessage().contains("engineType"));
    }*/


/*    @Test
    void createAircraft_shouldThrowException_WhenAircraftTypeNotFound() {
        when(aircraftDTOMapper.mapToEntity(requestDTO)).thenReturn(aircraftEntity);
        when(engineTypeRepository.findById(1L)).thenReturn(Optional.of(new EngineTypeEntity()));
        when(aircraftTypeRepository.findById(1L)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
                aircraftService.createAircraft(requestDTO));

        assertTrue(exception.getMessage().contains("aircraftType"));
    }*/

}
