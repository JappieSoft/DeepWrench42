package nl.novi.deepwrench42.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeRequestDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.repository.*;
import nl.novi.deepwrench42.services.EngineTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/engine-type")
@Tag(name = "Engine Type Management")
public class EngineTypeController {

    private final EngineTypeService engineTypeService;
    private final UrlHelper urlHelper;

    public EngineTypeController(EngineTypeService engineTypeService, UrlHelper urlHelper) {
        this.engineTypeService = engineTypeService;
        this.urlHelper = urlHelper;
    }

    @Operation(
            description = "Get details of all engine types",
            summary = "Get details of all engine types"
    )
    @GetMapping
    public ResponseEntity<List<EngineTypeResponseDTO>> getAllEngineTypes() {
        List<EngineTypeResponseDTO> engineTypes = engineTypeService.findAllEngineTypes();
        return ResponseEntity.ok(engineTypes);
    }

    @Operation(
            description = "Get details of a specific engine type id",
            summary = "Get details of a specific engine type"
    )
    @GetMapping("/{id}")
    public ResponseEntity<EngineTypeResponseDTO> getEngineTypeById(@PathVariable Long id) {
        EngineTypeResponseDTO engineType = engineTypeService.findEngineTypeById(id);
        return new ResponseEntity<EngineTypeResponseDTO>(engineType, HttpStatus.OK);
    }

    @Operation(
            description = "Create a new engine type, admin & leads only",
            summary = "Create a new engine type"
    )
    @PostMapping
    public ResponseEntity<EngineTypeResponseDTO> createEngineType(@Valid @RequestBody EngineTypeRequestDTO engineTypeInput) {
        EngineTypeResponseDTO newEngineType = engineTypeService.createEngineType(engineTypeInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newEngineType.getId())).body(newEngineType);
    }

    @Operation(
            description = "Update a specific engine type id, admin & leads only",
            summary = "Update a specific engine type"
    )
    @PutMapping("/{id}")
    public ResponseEntity<EngineTypeResponseDTO> updateEngineType(@PathVariable Long id, @Valid @RequestBody EngineTypeRequestDTO engineTypeInput) {
        EngineTypeResponseDTO updatedEngineType = engineTypeService.updateEngineType(id, engineTypeInput);
        return new ResponseEntity<>(updatedEngineType, HttpStatus.OK);
    }

    @Operation(
            description = "Delete a specific engine type with id, admin only",
            summary = "Delete a specific engine type"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEngineType(@PathVariable Long id) {
        engineTypeService.deleteEngineType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
