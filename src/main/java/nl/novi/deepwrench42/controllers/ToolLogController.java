package nl.novi.deepwrench42.controllers;

import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.toolLog.ToolLogRequestDTO;
import nl.novi.deepwrench42.dtos.toolLog.ToolLogResponseDTO;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.ToolLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tool-log")
public class ToolLogController {

    private final ToolLogService toolLogService;

    public ToolLogController(ToolLogService toolLogService) {
        this.toolLogService = toolLogService;
    }

    @GetMapping
    public ResponseEntity<List<ToolLogResponseDTO>> getAllToolLogs() {
        List<ToolLogResponseDTO> toolLogs = toolLogService.findAllToolLogs();
        return ResponseEntity.ok(toolLogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolLogResponseDTO> getToolLogById(@PathVariable Long id) {
        ToolLogResponseDTO toolLog = toolLogService.findToolLogById(id);
        return new ResponseEntity<ToolLogResponseDTO>(toolLog, HttpStatus.OK);
    }
}