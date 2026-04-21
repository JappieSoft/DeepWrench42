package nl.novi.deepwrench42.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeRequestDTO;
import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.AircraftTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircraft-type")
@Tag(name = "Aircraft Type Management")
public class AircraftTypeController {

    private final AircraftTypeService aircraftTypeService;
    private final UrlHelper urlHelper;

    public AircraftTypeController(AircraftTypeService aircraftTypeService, UrlHelper urlHelper) {
        this.aircraftTypeService = aircraftTypeService;
        this.urlHelper = urlHelper;
    }

    @Operation(
            description = "Get details of all aircraft types",
            summary = "Get details of all aircraft types"
    )
    @GetMapping
    public ResponseEntity<List<AircraftTypeResponseDTO>> getAllAircraftTypes() {
        List<AircraftTypeResponseDTO> aircraftTypes = aircraftTypeService.findAllAircraftTypes();
        return ResponseEntity.ok(aircraftTypes);
    }

    @Operation(
            description = "Get details of a specific aircraft type id",
            summary = "Get details of a specific aircraft type"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AircraftTypeResponseDTO> getAircraftTypeById(@PathVariable Long id) {
        AircraftTypeResponseDTO aircraftType = aircraftTypeService.findAircraftTypeById(id);
        return new ResponseEntity<AircraftTypeResponseDTO>(aircraftType, HttpStatus.OK);
    }

    @Operation(
            description = "Create a new aircraft type, admin & leads only",
            summary = "Create a new aircraft type"
    )
    @PostMapping
    public ResponseEntity<AircraftTypeResponseDTO> createAircraftType(@Valid @RequestBody AircraftTypeRequestDTO aircraftTypeInput) {
        AircraftTypeResponseDTO newAircraftType = aircraftTypeService.createAircraftType(aircraftTypeInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newAircraftType.getId())).body(newAircraftType);
    }

    @Operation(
            description = "Update a specific aircraft type id, admin & leads only",
            summary = "Update a specific aircraft type"
    )
    @PutMapping("/{id}")
    public ResponseEntity<AircraftTypeResponseDTO> updateAircraftType(@PathVariable Long id, @Valid @RequestBody AircraftTypeRequestDTO aircraftTypeInput) {
        AircraftTypeResponseDTO updatedAircraftType = aircraftTypeService.updateAircraftType(id, aircraftTypeInput);
        return new ResponseEntity<>(updatedAircraftType, HttpStatus.OK);
    }

    @Operation(
            description = "Delete a specific aircraft type with id, admin only",
            summary = "Delete a specific aircraft type"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraftType(@PathVariable Long id) {
        aircraftTypeService.deleteAircraftType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
