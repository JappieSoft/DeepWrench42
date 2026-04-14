package nl.novi.deepwrench42.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationRequestDTO;
import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationResponseDTO;
import nl.novi.deepwrench42.entities.StorageLocationEntity;
import nl.novi.deepwrench42.repository.StorageLocationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest()
public class StorageLocationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StorageLocationRepository storageLocationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        storageLocationRepository.deleteAll();
    }

    @Test
    @DisplayName("GET all Storage Locations, Working")
    void getAllStorageLocations_shouldReturnListOfStorageLocations() throws Exception {
        StorageLocationEntity storageLocationOne = new StorageLocationEntity();
        storageLocationOne.setLocation("Test");
        storageLocationOne.setRack("1");
        storageLocationOne.setShelf("2");
        storageLocationOne.setComments("3 comments");
        storageLocationRepository.save(storageLocationOne);

        StorageLocationEntity storageLocationTwo = new StorageLocationEntity();
        storageLocationTwo.setLocation("Test 2");
        storageLocationTwo.setRack("4");
        storageLocationTwo.setShelf("5");
        storageLocationTwo.setComments("6 comments");
        storageLocationRepository.save(storageLocationTwo);

        mockMvc.perform(get("/storage-location")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].location").value("Test"))
                .andExpect(jsonPath("$[0].rack").value("1"))
                .andExpect(jsonPath("$[0].shelf").value("2"))
                .andExpect(jsonPath("$[0].comments").value("3 comments"))

                .andExpect(jsonPath("$[1].location").value("Test 2"))
                .andExpect(jsonPath("$[1].rack").value("4"))
                .andExpect(jsonPath("$[1].shelf").value("5"))
                .andExpect(jsonPath("$[1].comments").value("6 comments"));
    }

    @Test
    @DisplayName("GET Storage Location id 1, Working")
    void getStorageLocationById_shouldReturnStorageLocation_whenStorageLocationExists() throws Exception {
        StorageLocationEntity storageLocationThree = new StorageLocationEntity();
        storageLocationThree.setLocation("Test 3");
        storageLocationThree.setRack("7");
        storageLocationThree.setShelf("8");
        storageLocationThree.setComments("9 comments");
        Long storageId = storageLocationRepository.save(storageLocationThree).getId();

        mockMvc.perform(get("/storage-location/{id}", storageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("Test 3"))
                .andExpect(jsonPath("$.rack").value("7"))
                .andExpect(jsonPath("$.shelf").value("8"))
                .andExpect(jsonPath("$.comments").value("9 comments"));
    }

    @Test
    @DisplayName("GET Storage Location id 999, Storage Location Not Found")
    void getStorageLocationById_shouldReturnNotFound_whenStorageLocationDoesNotExist() throws Exception {
        mockMvc.perform(get("/storage-location/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST Storage Location, Working")
    void createStorageLocation_shouldCreateAndReturnStorageLocation() throws Exception {
        StorageLocationRequestDTO storageLocationRequestDTO = new StorageLocationRequestDTO();
        storageLocationRequestDTO.setLocation("DTO");
        storageLocationRequestDTO.setRack("10");
        storageLocationRequestDTO.setShelf("20");
        storageLocationRequestDTO.setComments("DTO comments");

        StorageLocationResponseDTO storageLocationResponseDTO = new StorageLocationResponseDTO();
        storageLocationResponseDTO.setLocation("DTO");
        storageLocationResponseDTO.setRack("10");
        storageLocationResponseDTO.setShelf("20");
        storageLocationResponseDTO.setComments("DTO comments");

        var response = mockMvc.perform(post("/storage-location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(storageLocationRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.location").value("DTO"))
                .andExpect(jsonPath("$.rack").value("10"))
                .andExpect(jsonPath("$.shelf").value("20"))
                .andExpect(jsonPath("$.comments").value("DTO comments"))
                .andReturn();

        StorageLocationResponseDTO storageLocationResult = objectMapper.readValue(response.getResponse().getContentAsString(), StorageLocationResponseDTO.class);

        Assertions.assertEquals(storageLocationResponseDTO.getLocation(), storageLocationResult.getLocation());
        Assertions.assertEquals(storageLocationResponseDTO.getRack(), storageLocationResult.getRack());
        Assertions.assertEquals(storageLocationResponseDTO.getShelf(), storageLocationResult.getShelf());
        Assertions.assertEquals(storageLocationResponseDTO.getComments(), storageLocationResult.getComments());
    }

    @Test
    @DisplayName("PUT Storage Location, Working")
    void updateStorageLocation_shouldUpdateAndReturnStorageLocation_whenStorageLocationExists() throws Exception {
        StorageLocationEntity storageLocation = new StorageLocationEntity();
        storageLocation.setLocation("Created Entity");
        storageLocation.setRack("10");
        storageLocation.setShelf("20");
        storageLocation.setComments("Entity comments");
        Long storageLocationId = storageLocationRepository.save(storageLocation).getId();

        StorageLocationRequestDTO storageLocationRequestDTO = new StorageLocationRequestDTO();
        storageLocationRequestDTO.setLocation("Updated DTO");
        storageLocationRequestDTO.setRack("11");
        storageLocationRequestDTO.setShelf("21");
        storageLocationRequestDTO.setComments("Updated DTO comments");

        mockMvc.perform(put("/storage-location/{id}", storageLocationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(storageLocationRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("Updated DTO"))
                .andExpect(jsonPath("$.rack").value("11"))
                .andExpect(jsonPath("$.shelf").value("21"))
                .andExpect(jsonPath("$.comments").value("Updated DTO comments"));
    }

    @Test
    @DisplayName("PUT Storage Location, Storage Location Not Found")
    void updateStorageLocation_shouldReturnNotFound_whenStorageLocationDoesNotExist() throws Exception {
        StorageLocationRequestDTO storageLocationRequestDTO = new StorageLocationRequestDTO();
        storageLocationRequestDTO.setLocation("Update attempt DTO");
        storageLocationRequestDTO.setRack("12");
        storageLocationRequestDTO.setShelf("22");
        storageLocationRequestDTO.setComments("Not updated DTO comments");

        mockMvc.perform(put("/storage-location/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(storageLocationRequestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE Storage Location, Working")
    void deleteStorageLocation_shouldDeleteStorageLocation_whenStorageLocationExists() throws Exception {
        StorageLocationEntity storageLocation = new StorageLocationEntity();
        storageLocation.setLocation("To delete DTO");
        storageLocation.setRack("100");
        storageLocation.setShelf("200");
        storageLocation.setComments("Delete DTO comments");
        Long storageLocationId = storageLocationRepository.save(storageLocation).getId();

        mockMvc.perform(delete("/storage-location/{id}", storageLocationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/storage-location/{id}", storageLocationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE Storage Location, Storage Location Not Found")
    void deleteStorageLocation_shouldReturnNotFound_whenStorageLocationDoesNotExist() throws Exception {
        mockMvc.perform(delete("/storage-location/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}


