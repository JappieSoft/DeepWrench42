package nl.novi.deepwrench42.controllers;

import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.tool.ToolRequestDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.ToolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tool")
public class ToolController {

    private final ToolService toolService;
    private final UrlHelper urlHelper;

    public ToolController(ToolService toolService, UrlHelper urlHelper) {
        this.toolService = toolService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<ToolResponseDTO>> getAllTools() {
        List<ToolResponseDTO> tools = toolService.findAllTools();
        return ResponseEntity.ok(tools);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolResponseDTO> getToolById(@PathVariable Long id) {
        ToolResponseDTO tool = toolService.findToolById(id);
        if (tool == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<ToolResponseDTO>(tool, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ToolResponseDTO> createTool(@Valid @RequestBody ToolRequestDTO toolInput) {
        ToolResponseDTO newTool = toolService.createTool(toolInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newTool.getId())).body(newTool);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToolResponseDTO> updateTool(@PathVariable Long id, @Valid @RequestBody ToolRequestDTO toolInput) {
        ToolResponseDTO updatedTool = toolService.updateTool(id, toolInput);
        return new ResponseEntity<>(updatedTool, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
