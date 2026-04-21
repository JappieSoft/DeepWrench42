package nl.novi.deepwrench42.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.novi.deepwrench42.dtos.toolLog.ToolLogResponseDTO;
import nl.novi.deepwrench42.services.ToolLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tool-log")
@Tag(name = "Tool Log")
public class ToolLogController {

    private final ToolLogService toolLogService;

    public ToolLogController(ToolLogService toolLogService) {
        this.toolLogService = toolLogService;
    }

    @Operation(
            description = "Get all tool log entry's, admin & leads only",
            summary = "Get complete tool log"
    )
    @GetMapping
    public ResponseEntity<List<ToolLogResponseDTO>> getAllToolLogs() {
        List<ToolLogResponseDTO> toolLogs = toolLogService.findAllToolLogs();
        return ResponseEntity.ok(toolLogs);
    }

    @Operation(
            description = "Get a specific tool log id, admin & leads only",
            summary = "Get a specific tool log id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ToolLogResponseDTO> getToolLogById(@PathVariable Long id) {
        ToolLogResponseDTO toolLog = toolLogService.findToolLogById(id);
        return new ResponseEntity<ToolLogResponseDTO>(toolLog, HttpStatus.OK);
    }
}