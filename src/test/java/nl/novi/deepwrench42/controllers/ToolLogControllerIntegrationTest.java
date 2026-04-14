package nl.novi.deepwrench42.controllers;

import nl.novi.deepwrench42.entities.EquipmentStatus;
import nl.novi.deepwrench42.entities.ToolLogActionType;
import nl.novi.deepwrench42.entities.ToolLogEntity;
import nl.novi.deepwrench42.repository.ToolLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest()
public class ToolLogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToolLogRepository toolLogRepository;

    @BeforeEach
    public void setup() {
        toolLogRepository.deleteAll();
    }

    @Test
    @DisplayName("GET all Tool-Logs, Working")
    void getAllToolLogs_shouldReturnListOfToolLogs() throws Exception {
        ToolLogEntity toolLogOne = new ToolLogEntity();
        toolLogOne.setTimeStamp(LocalDateTime.parse("2000-01-01T01:02:03"));
        toolLogOne.setActionType(ToolLogActionType.valueOf("CHECK_OUT"));
        toolLogOne.setActionResult(EquipmentStatus.valueOf("CHECKED_OUT"));
        toolLogOne.setActionBy("Tester");
        toolLogOne.setItemNumber("1");
        toolLogOne.setItemType("2");
        toolLogOne.setItemName("Test Object");
        toolLogOne.setAtaCode(1234);
        toolLogOne.setPartNumber("3");
        toolLogOne.setSerialNumber("4");
        toolLogOne.setManufacturer("5");
        toolLogOne.setAircraftNumber("6");
        toolLogOne.setComments("Many Comments");
        toolLogRepository.save(toolLogOne);

        ToolLogEntity toolLogTwo = new ToolLogEntity();
        toolLogTwo.setTimeStamp(LocalDateTime.parse("2020-02-02T03:04:05"));
        toolLogTwo.setActionType(ToolLogActionType.valueOf("CHECK_IN"));
        toolLogTwo.setActionResult(EquipmentStatus.valueOf("CHECKED_IN"));
        toolLogTwo.setActionBy("Tester 2");
        toolLogTwo.setItemNumber("10");
        toolLogTwo.setItemType("20");
        toolLogTwo.setItemName("Test Object 2");
        toolLogTwo.setAtaCode(4321);
        toolLogTwo.setPartNumber("30");
        toolLogTwo.setSerialNumber("40");
        toolLogTwo.setManufacturer("50");
        toolLogTwo.setAircraftNumber("60");
        toolLogTwo.setComments("Many More Comments");
        toolLogRepository.save(toolLogTwo);

        mockMvc.perform(get("/tool-log")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].timeStamp").value(containsString("2000-01-01T01:02:03")))
                .andExpect(jsonPath("$[0].actionType").value(containsString("CHECK_OUT")))
                .andExpect(jsonPath("$[0].actionResult").value(containsString("CHECKED_OUT")))
                .andExpect(jsonPath("$[0].actionBy").value("Tester"))
                .andExpect(jsonPath("$[0].itemNumber").value("1"))
                .andExpect(jsonPath("$[0].itemType").value("2"))
                .andExpect(jsonPath("$[0].itemName").value("Test Object"))
                .andExpect(jsonPath("$[0].ataCode").value(1234))
                .andExpect(jsonPath("$[0].partNumber").value("3"))
                .andExpect(jsonPath("$[0].serialNumber").value("4"))
                .andExpect(jsonPath("$[0].manufacturer").value("5"))
                .andExpect(jsonPath("$[0].aircraftNumber").value("6"))
                .andExpect(jsonPath("$[0].comments").value("Many Comments"))

                .andExpect(jsonPath("$[1].timeStamp").value(containsString("2020-02-02T03:04:05")))
                .andExpect(jsonPath("$[1].actionType").value(containsString("CHECK_IN")))
                .andExpect(jsonPath("$[1].actionResult").value(containsString("CHECKED_IN")))
                .andExpect(jsonPath("$[1].actionBy").value("Tester 2"))
                .andExpect(jsonPath("$[1].itemNumber").value("10"))
                .andExpect(jsonPath("$[1].itemType").value("20"))
                .andExpect(jsonPath("$[1].itemName").value("Test Object 2"))
                .andExpect(jsonPath("$[1].ataCode").value(4321))
                .andExpect(jsonPath("$[1].partNumber").value("30"))
                .andExpect(jsonPath("$[1].serialNumber").value("40"))
                .andExpect(jsonPath("$[1].manufacturer").value("50"))
                .andExpect(jsonPath("$[1].aircraftNumber").value("60"))
                .andExpect(jsonPath("$[1].comments").value("Many More Comments"));
    }

    @Test
    @DisplayName("GET Tool-Log id 1, Working")
    void getToolLogById_shouldReturnToolLog_whenToolLogExists() throws Exception {
        ToolLogEntity toolLogThree = new ToolLogEntity();
        toolLogThree.setTimeStamp(LocalDateTime.parse("2222-11-22T10:11:12"));
        toolLogThree.setActionType(ToolLogActionType.valueOf("CHECK_OUT"));
        toolLogThree.setActionResult(EquipmentStatus.valueOf("CHECKED_OUT"));
        toolLogThree.setActionBy("The Third Person");
        toolLogThree.setItemNumber("100");
        toolLogThree.setItemType("200");
        toolLogThree.setItemName("Something");
        toolLogThree.setAtaCode(5678);
        toolLogThree.setPartNumber("300");
        toolLogThree.setSerialNumber("400");
        toolLogThree.setManufacturer("500");
        toolLogThree.setAircraftNumber("600");
        toolLogThree.setComments("No Comments");
        Long storageId = toolLogRepository.save(toolLogThree).getId();

        mockMvc.perform(get("/tool-log/{id}", storageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeStamp").value(containsString("2222-11-22T10:11:12")))
                .andExpect(jsonPath("$.actionType").value(containsString("CHECK_OUT")))
                .andExpect(jsonPath("$.actionResult").value(containsString("CHECKED_OUT")))
                .andExpect(jsonPath("$.actionBy").value("The Third Person"))
                .andExpect(jsonPath("$.itemNumber").value("100"))
                .andExpect(jsonPath("$.itemType").value("200"))
                .andExpect(jsonPath("$.itemName").value("Something"))
                .andExpect(jsonPath("$.ataCode").value(5678))
                .andExpect(jsonPath("$.partNumber").value("300"))
                .andExpect(jsonPath("$.serialNumber").value("400"))
                .andExpect(jsonPath("$.manufacturer").value("500"))
                .andExpect(jsonPath("$.aircraftNumber").value("600"))
                .andExpect(jsonPath("$.comments").value("No Comments"));
    }

    @Test
    @DisplayName("GET Tool-Log id 999, Log Not Found")
    void getToolLogById_shouldReturnNotFound_whenToolLogDoesNotExist() throws Exception {
        mockMvc.perform(get("/tool-log/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
