package nl.novi.deepwrench42.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.aircraft.AircraftRequestDTO;
import nl.novi.deepwrench42.dtos.aircraft.AircraftResponseDTO;
import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;
import nl.novi.deepwrench42.entities.AircraftEntity;
import nl.novi.deepwrench42.entities.AircraftTypeEntity;
import nl.novi.deepwrench42.entities.EngineTypeEntity;
import nl.novi.deepwrench42.repository.AircraftRepository;
import nl.novi.deepwrench42.repository.AircraftTypeRepository;
import nl.novi.deepwrench42.repository.EngineTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest()
public class AircraftControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private AircraftTypeRepository aircraftTypeRepository;
    @Autowired
    private EngineTypeRepository engineTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AircraftTypeEntity aircraftType1;
    private AircraftTypeEntity aircraftType2;
    private EngineTypeEntity engineType1;
    private EngineTypeEntity engineType2;

    @BeforeEach
    void setUp() {
        aircraftType1 = new AircraftTypeEntity();
        aircraftType1.setManufacturer("Airbus");
        aircraftType1.setMainType("A330");
        aircraftType1.setSubType("A330-300");
        aircraftType1 = aircraftTypeRepository.save(aircraftType1);

        aircraftType2 = new AircraftTypeEntity();
        aircraftType2.setManufacturer("Boeing");
        aircraftType2.setMainType("B787");
        aircraftType2.setSubType("B787-800");
        aircraftType2 = aircraftTypeRepository.save(aircraftType2);

        engineType1 = new EngineTypeEntity();
        engineType1.setManufacturer("Pratt & Whitney");
        engineType1.setMainType("PW4000");
        engineType1.setSubType("PW4168");
        engineType1 = engineTypeRepository.save(engineType1);

        engineType2 = new EngineTypeEntity();
        engineType2.setManufacturer("General Electric");
        engineType2.setMainType("GenX");
        engineType2.setSubType("GenX-1A");
        engineType2 = engineTypeRepository.save(engineType2);

        aircraftRepository.deleteAll();
    }

    @DirtiesContext
    @Test
    @Transactional
    @DisplayName("GET all aircraft, Working")
    void getAllAircraft_shouldReturnListOfAircraft() throws Exception {
        AircraftEntity aircraftOne = new AircraftEntity();
        aircraftOne.setShipNumber("TestShip");
        aircraftOne.setRegistration("1234");
        aircraftOne.setAircraftType(aircraftType1);
        aircraftOne.setEngineType(engineType1);
        aircraftRepository.save(aircraftOne);

        AircraftEntity aircraftTwo = new AircraftEntity();
        aircraftTwo.setShipNumber("TestShip2");
        aircraftTwo.setRegistration("4568");
        aircraftTwo.setAircraftType(aircraftType1);
        aircraftTwo.setEngineType(engineType1);
        aircraftRepository.save(aircraftTwo);

        mockMvc.perform(get("/aircraft")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].shipNumber").value("TestShip"))
                .andExpect(jsonPath("$[0].registration").value("1234"))
                .andExpect(jsonPath("$[0].aircraftType.id").value(aircraftType1.getId()))
                .andExpect(jsonPath("$[0].engineType.id").value(engineType1.getId()))

                .andExpect(jsonPath("$[1].shipNumber").value("TestShip2"))
                .andExpect(jsonPath("$[1].registration").value("4568"))
                .andExpect(jsonPath("$[1].aircraftType.id").value(aircraftType1.getId()))
                .andExpect(jsonPath("$[1].engineType.id").value(engineType1.getId()));
    }

    @DirtiesContext
    @Test
    @Transactional
    @DisplayName("GET aircraft id 1, Working")
    void getAircraftById_shouldReturnAircraft_whenAircraftExists() throws Exception {
        AircraftEntity aircraftThree = new AircraftEntity();
        aircraftThree.setShipNumber("TestShip3");
        aircraftThree.setRegistration("78910");
        aircraftThree.setAircraftType(aircraftType1);
        aircraftThree.setEngineType(engineType1);
        Long storageId = aircraftRepository.save(aircraftThree).getId();
        System.out.println("storageId: " + storageId);

        mockMvc.perform(get("/aircraft/{id}", storageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipNumber").value("TestShip3"))
                .andExpect(jsonPath("$.registration").value("78910"))
                .andExpect(jsonPath("$.aircraftType.id").value(aircraftType1.getId()))
                .andExpect(jsonPath("$.engineType.id").value(engineType1.getId()));
    }

    @DirtiesContext
    @Test
    @DisplayName("GET aircraft id 999, Aircraft Not Found")
    void getAircraftById_shouldReturnNotFound_whenAircraftDoesNotExist() throws Exception {
        mockMvc.perform(get("/aircraft/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @DirtiesContext
    @Test
    @Transactional
    @DisplayName("POST aircraft, Working")
    void createAircraft_shouldCreateAndReturnAircraft() throws Exception {
        AircraftRequestDTO aircraftRequestDTO = new AircraftRequestDTO();
        aircraftRequestDTO.setShipNumber("4561");
        aircraftRequestDTO.setRegistration("PH-DTO");
        aircraftRequestDTO.setAircraftTypeId(2L);
        aircraftRequestDTO.setEngineTypeId(2L);

        AircraftTypeResponseDTO aircraftTypeResponseDTO = new AircraftTypeResponseDTO();
        aircraftTypeResponseDTO.setId(2L);
        aircraftTypeResponseDTO.setManufacturer("Boeing");
        aircraftTypeResponseDTO.setMainType("B787");
        aircraftTypeResponseDTO.setSubType("B787-800");

        EngineTypeResponseDTO engineTypeResponseDTO = new EngineTypeResponseDTO();
        engineTypeResponseDTO.setId(2L);
        engineTypeResponseDTO.setManufacturer("General Electric");
        engineTypeResponseDTO.setMainType("GenX");
        engineTypeResponseDTO.setSubType("GenX-1A");

        AircraftResponseDTO aircraftResponseDTO = new AircraftResponseDTO();
        aircraftResponseDTO.setShipNumber("4561");
        aircraftResponseDTO.setRegistration("PH-DTO");
        aircraftResponseDTO.setAircraftType(aircraftTypeResponseDTO);
        aircraftResponseDTO.setEngineType(engineTypeResponseDTO);


        var response = mockMvc.perform(post("/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aircraftRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shipNumber").value("4561"))
                .andExpect(jsonPath("$.registration").value("PH-DTO"))
                .andExpect(jsonPath("$.aircraftType.id").value(aircraftType2.getId()))
                .andExpect(jsonPath("$.engineType.id").value(engineType2.getId()))
                .andReturn();

        AircraftResponseDTO aircraftResult = objectMapper.readValue(response.getResponse().getContentAsString(), AircraftResponseDTO.class);

        assertEquals(aircraftResponseDTO.getShipNumber(), aircraftResult.getShipNumber());
        assertEquals(aircraftResponseDTO.getRegistration(), aircraftResult.getRegistration());
        assertEquals(aircraftResponseDTO.getAircraftType().getId(), aircraftResult.getAircraftType().getId());
        assertEquals(aircraftResponseDTO.getEngineType().getId(), aircraftResult.getEngineType().getId());
    }

    @DirtiesContext
    @Test
    @Transactional
    @DisplayName("PUT aircraft, Working")
    void updateAircraft_shouldUpdateAndReturnAircraft_whenAircraftExists() throws Exception {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setShipNumber("3692");
        aircraft.setRegistration("1032");
        aircraft.setAircraftType(aircraftType1);
        aircraft.setEngineType(engineType1);
        Long aircraftId = aircraftRepository.save(aircraft).getId();

        AircraftRequestDTO aircraftRequestDTO = new AircraftRequestDTO();
        aircraftRequestDTO.setShipNumber("3322");
        aircraftRequestDTO.setRegistration("N822NW");
        aircraftRequestDTO.setAircraftTypeId(1L);
        aircraftRequestDTO.setEngineTypeId(2L);

        mockMvc.perform(put("/aircraft/{id}", aircraftId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aircraftRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipNumber").value("3322"))
                .andExpect(jsonPath("$.registration").value("N822NW"))
                .andExpect(jsonPath("$.aircraftType.id").value(aircraftType1.getId()))
                .andExpect(jsonPath("$.engineType.id").value(engineType2.getId()));
    }

    @DirtiesContext
    @Test
    @DisplayName("PUT aircraft, Aircraft Not Found")
    void updateAircraft_shouldReturnNotFound_whenAircraftDoesNotExist() throws Exception {
        AircraftRequestDTO aircraftRequestDTO = new AircraftRequestDTO();
        aircraftRequestDTO.setShipNumber("3621");
        aircraftRequestDTO.setRegistration("N362DN");
        aircraftRequestDTO.setAircraftTypeId(2L);
        aircraftRequestDTO.setEngineTypeId(2L);

        mockMvc.perform(put("/aircraft/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aircraftRequestDTO)))
                .andExpect(status().isNotFound());
    }

    @DirtiesContext
    @Test
    @Transactional
    @DisplayName("DELETE aircraft, Working")
    void deleteAircraft_shouldDeleteAircraft_whenAircraftExists() throws Exception {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setShipNumber("5105");
        aircraft.setRegistration("N100Z");
        aircraft.setAircraftType(aircraftType1);
        aircraft.setEngineType(engineType1);
        Long aircraftId = aircraftRepository.save(aircraft).getId();

        mockMvc.perform(delete("/aircraft/{id}", aircraftId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/aircraft/{id}", aircraftId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @DirtiesContext
    @Test
    @DisplayName("DELETE aircraft, Aircraft Not Found")
    void deleteAircraft_shouldReturnNotFound_whenAircraftDoesNotExist() throws Exception {
        mockMvc.perform(delete("/aircraft/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}


