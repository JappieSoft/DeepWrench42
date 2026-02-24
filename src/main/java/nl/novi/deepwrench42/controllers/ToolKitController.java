package nl.novi.deepwrench42.controllers;

import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitRequestDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.ToolKitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tool-kit")
public class ToolKitController {

    private final ToolKitService toolKitService;
    private final UrlHelper urlHelper;

    public ToolKitController(ToolKitService toolKitService, UrlHelper urlHelper) {
        this.toolKitService = toolKitService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<ToolKitResponseDTO>> getAllToolKits() {
        List<ToolKitResponseDTO> toolKits = toolKitService.findAllToolKits();
        return ResponseEntity.ok(toolKits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolKitResponseDTO> getToolKitById(@PathVariable Long id) {
        ToolKitResponseDTO toolKit = toolKitService.findToolKitById(id);
        if (toolKit == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<ToolKitResponseDTO>(toolKit, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ToolKitResponseDTO> createToolKit(@Valid @RequestBody ToolKitRequestDTO toolKitInput) {
        ToolKitResponseDTO newToolKit = toolKitService.createToolKit(toolKitInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newToolKit.getId())).body(newToolKit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToolKitResponseDTO> updateToolKit(@PathVariable Long id, @Valid @RequestBody ToolKitRequestDTO toolKitInput) {
        ToolKitResponseDTO updatedToolKit = toolKitService.updateToolKit(id, toolKitInput);
        return new ResponseEntity<>(updatedToolKit, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToolKit(@PathVariable Long id) {
        toolKitService.deleteToolKit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
