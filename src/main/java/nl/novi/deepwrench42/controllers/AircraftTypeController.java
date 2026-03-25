package nl.novi.deepwrench42.controllers;

import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeRequestDTO;
import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.exceptions.ForeignKeyViolationException;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.repository.AircraftRepository;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import nl.novi.deepwrench42.services.AircraftTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircraft-type")
public class AircraftTypeController {

    private final AircraftTypeService aircraftTypeService;
    private final AircraftRepository aircraftRepository;
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;
    private final UrlHelper urlHelper;
    
    public AircraftTypeController(AircraftTypeService aircraftTypeService, AircraftRepository aircraftRepository, ToolRepository toolRepository, ToolKitRepository toolKitRepository, UrlHelper urlHelper) {
        this.aircraftTypeService = aircraftTypeService;
        this.aircraftRepository = aircraftRepository;
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<AircraftTypeResponseDTO>> getAllAircraftTypes() {
        List<AircraftTypeResponseDTO> aircraftTypes = aircraftTypeService.findAllAircraftTypes();
        return ResponseEntity.ok(aircraftTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftTypeResponseDTO> getAircraftTypeById(@PathVariable Long id) {
        AircraftTypeResponseDTO aircraftType = aircraftTypeService.findAircraftTypeById(id);
        return new ResponseEntity<AircraftTypeResponseDTO>(aircraftType, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AircraftTypeResponseDTO> createAircraftType(@Valid @RequestBody AircraftTypeRequestDTO aircraftTypeInput) {
        AircraftTypeResponseDTO newAircraftType = aircraftTypeService.createAircraftType(aircraftTypeInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newAircraftType.getId())).body(newAircraftType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftTypeResponseDTO> updateAircraftType(@PathVariable Long id, @Valid @RequestBody AircraftTypeRequestDTO aircraftTypeInput) {
        AircraftTypeResponseDTO updatedAircraftType = aircraftTypeService.updateAircraftType(id, aircraftTypeInput);
        return new ResponseEntity<>(updatedAircraftType, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraftType(@PathVariable Long id) {
        if (aircraftRepository.existsByAircraftTypeId(id)) {
            throw new ForeignKeyViolationException("Aircraft type is still referenced by an aircraft.");
        }
        if (toolRepository.existsByAircraftTypeId(id)) {
            throw new ForeignKeyViolationException("Aircraft type is still referenced by a tool.");
        }
        if (toolKitRepository.existsByAircraftTypeId(id)) {
            throw new ForeignKeyViolationException("Aircraft type is still referenced by a tool kit.");
        }
        aircraftTypeService.deleteAircraftType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
