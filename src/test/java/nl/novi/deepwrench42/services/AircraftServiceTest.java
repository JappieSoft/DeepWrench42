package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.aircraft.AircraftRequestDTO;
import nl.novi.deepwrench42.dtos.aircraft.AircraftResponseDTO;
import nl.novi.deepwrench42.entities.AircraftEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.repository.AircraftRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@Sql("/data-test.sql")
class AircraftServiceTest {

    @Autowired AircraftService aircraftService;
    @Autowired AircraftRepository aircraftRepository;

    @Test
    @DirtiesContext
    void findAllAircraft_shouldReturnList() {
        List<AircraftResponseDTO> result = aircraftService.findAllAircraft();
        assertEquals(4, result.size());
    }

    @Test
    @DirtiesContext
    void findAircraftById_shouldReturnAircraftResponseDTO_whenAircraftExists() {
        AircraftResponseDTO result = aircraftService.findAircraftById(1L);

        assertEquals("3311", result.getShipNumber());
        assertEquals("N811NW", result.getRegistration());
    }

    @Test
    @DirtiesContext
    void findAircraftById_shouldThrow_whenAircraftDoesNotExist() {
        Long id = 999L;

        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                () -> aircraftService.findAircraftById(id));
        assertEquals("Aircraft " + id + " not found", ex.getMessage());
    }

    @Test
    @DirtiesContext
    void createAircraft_shouldReturnCreatedAircraft() {
        AircraftRequestDTO requestDto = new AircraftRequestDTO();
        requestDto.setShipNumber("9999");
        requestDto.setRegistration("CREATE1");
        requestDto.setAircraftTypeId(1L);
        requestDto.setEngineTypeId(1L);

        AircraftResponseDTO result = aircraftService.createAircraft(requestDto);

        assertEquals("9999", result.getShipNumber());
        assertEquals("CREATE1", result.getRegistration());
        assertNotNull(result.getId());
        assertEquals(5, aircraftRepository.count());
    }

    @Test
    @DirtiesContext
    void createAircraft_shouldThrow_AircraftType_NotFoundException() {
        AircraftRequestDTO requestDto = new AircraftRequestDTO();
        Long aircraftTypeId = 999L;
        requestDto.setShipNumber("7777");
        requestDto.setRegistration("CREATE3");
        requestDto.setAircraftTypeId(aircraftTypeId);
        requestDto.setEngineTypeId(1L);

        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                () -> aircraftService.createAircraft(requestDto));
        assertEquals("Aircraft Type " + aircraftTypeId + " not found", ex.getMessage());
    }

    @Test
    @DirtiesContext
    void createAircraft_shouldThrow_EngineType_NotFoundException() {
        AircraftRequestDTO requestDto = new AircraftRequestDTO();
        Long engineTypeId = 999L;
        requestDto.setShipNumber("8888");
        requestDto.setRegistration("CREATE4");
        requestDto.setAircraftTypeId(1L);
        requestDto.setEngineTypeId(engineTypeId);

        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                () -> aircraftService.createAircraft(requestDto));
        assertEquals("Engine Type " + engineTypeId + " not found", ex.getMessage());
    }

    @Test
    @DirtiesContext
    void updateAircraft_shouldReturnUpdatedAircraft() {
        Long id = 4L;
        AircraftRequestDTO requestDto = new AircraftRequestDTO();
        requestDto.setShipNumber("5555");
        requestDto.setRegistration("UPDATE2");
        requestDto.setAircraftTypeId(5L);
        requestDto.setEngineTypeId(5L);

        AircraftResponseDTO result = aircraftService.updateAircraft(id, requestDto);

        assertEquals("5555", result.getShipNumber());
        assertEquals("UPDATE2", result.getRegistration());

        AircraftEntity updated = aircraftRepository.findById(id).orElseThrow();
        assertEquals("5555", updated.getShipNumber());
    }

    @Test
    @DirtiesContext
    void updateAircraft_shouldThrow_whenAircraftDoesNotExist() {
        Long id = 999L;
        AircraftRequestDTO dto = new AircraftRequestDTO();

        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                () -> aircraftService.updateAircraft(id, dto));
        assertEquals("Aircraft " + id + " not found", ex.getMessage());
    }

    @Test
    @DirtiesContext
    void updateAircraft_shouldThrow_AircraftType_NotFoundException() {
        Long id = 4L;
        Long aircraftTypeId = 999L;
        AircraftRequestDTO requestDto = new AircraftRequestDTO();
        requestDto.setShipNumber("4444");
        requestDto.setRegistration("UPDATE3");
        requestDto.setAircraftTypeId(aircraftTypeId);
        requestDto.setEngineTypeId(1L);

        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                () -> aircraftService.updateAircraft(id, requestDto));
        assertEquals("Aircraft Type " + aircraftTypeId + " not found", ex.getMessage());
    }

    @Test
    @DirtiesContext
    void updateAircraft_shouldThrow_EngineType_NotFoundException() {
        Long id = 4L;
        Long engineTypeId = 999L;
        AircraftRequestDTO requestDto = new AircraftRequestDTO();
        requestDto.setShipNumber("3333");
        requestDto.setRegistration("UPDATE4");
        requestDto.setAircraftTypeId(1L);
        requestDto.setEngineTypeId(engineTypeId);

        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                () -> aircraftService.updateAircraft(id, requestDto));
        assertEquals("Engine Type " + engineTypeId + " not found", ex.getMessage());
    }


    @Test
    @DirtiesContext
    void deleteAircraft_shouldDelete_whenAircraftExists() {
        Long id = 1L;
        int before = (int) aircraftRepository.count();

        aircraftService.deleteAircraft(id);

        assertEquals(before - 1, aircraftRepository.count());
        assertTrue(aircraftRepository.findById(id).isEmpty());
    }

    @Test
    @DirtiesContext
    void deleteAircraft_shouldThrow_whenAircraftDoesNotExist() {
        Long id = 999L;

        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class,
                () -> aircraftService.deleteAircraft(id));
        assertEquals("Aircraft " + id + " not found", ex.getMessage());
    }
}