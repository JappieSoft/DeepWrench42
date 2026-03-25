package nl.novi.deepwrench42.controllers;

import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeRequestDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;
import nl.novi.deepwrench42.exceptions.ForeignKeyViolationException;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.repository.*;
import nl.novi.deepwrench42.services.EngineTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/engine-type")
public class EngineTypeController {

    private final EngineTypeService engineTypeService;
    private final AircraftRepository aircraftRepository;
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;
    private final UrlHelper urlHelper;

    public EngineTypeController(EngineTypeService engineTypeService, AircraftRepository aircraftRepository, ToolRepository toolRepository, ToolKitRepository toolKitRepository , UrlHelper urlHelper) {
        this.engineTypeService = engineTypeService;
        this.aircraftRepository = aircraftRepository;
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<EngineTypeResponseDTO>> getAllEngineTypes() {
        List<EngineTypeResponseDTO> engineTypes = engineTypeService.findAllEngineTypes();
        return ResponseEntity.ok(engineTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EngineTypeResponseDTO> getEngineTypeById(@PathVariable Long id) {
        EngineTypeResponseDTO engineType = engineTypeService.findEngineTypeById(id);
        return new ResponseEntity<EngineTypeResponseDTO>(engineType, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EngineTypeResponseDTO> createEngineType(@Valid @RequestBody EngineTypeRequestDTO engineTypeInput) {
        EngineTypeResponseDTO newEngineType = engineTypeService.createEngineType(engineTypeInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newEngineType.getId())).body(newEngineType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EngineTypeResponseDTO> updateEngineType(@PathVariable Long id, @Valid @RequestBody EngineTypeRequestDTO engineTypeInput) {
        EngineTypeResponseDTO updatedEngineType = engineTypeService.updateEngineType(id, engineTypeInput);
        return new ResponseEntity<>(updatedEngineType, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEngineType(@PathVariable Long id) {
        if (aircraftRepository.existsByAircraftTypeId(id)) {
            throw new ForeignKeyViolationException("Engine type is still referenced by an aircraft.");
        }
        if (toolRepository.existsByEngineTypeId(id)) {
            throw new ForeignKeyViolationException("Engine type is still referenced by a tool.");
        }
        if (toolKitRepository.existsByEngineTypeId(id)) {
            throw new ForeignKeyViolationException("Engine type is still referenced by a tool kit.");
        }
        engineTypeService.deleteEngineType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
