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
    private final UrlHelper urlHelper;

    public ToolLogController(ToolLogService toolLogService, UrlHelper urlHelper) {
        this.toolLogService = toolLogService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<ToolLogResponseDTO>> getAllToolLogs() {
        List<ToolLogResponseDTO> toolLogs = toolLogService.findAllToolLogs();
        return ResponseEntity.ok(toolLogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolLogResponseDTO> getToolLogById(@PathVariable Long id) {
        ToolLogResponseDTO toolLog = toolLogService.findToolLogById(id);
        if (toolLog == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<ToolLogResponseDTO>(toolLog, HttpStatus.OK);
    }

    // Below will not be things to use due to the append only character! //
/*

    @PostMapping
    public ResponseEntity<ToolLogResponseDTO> createToolLog(@Valid @RequestBody ToolLogRequestDTO toolLogInput) {
        ToolLogResponseDTO newToolLog = toolLogService.createToolLog(toolLogInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newToolLog.getId())).body(newToolLog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToolLogResponseDTO> updateToolLog(@PathVariable Long id, @Valid @RequestBody ToolLogRequestDTO toolLogInput) {
        ToolLogResponseDTO updatedToolLog = toolLogService.updateToolLog(id, toolLogInput);
        return new ResponseEntity<>(updatedToolLog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToolLog(@PathVariable Long id) {
        toolLogService.deleteToolLog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*//*
     */
}