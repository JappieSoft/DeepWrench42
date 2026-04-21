package nl.novi.deepwrench42.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.aircraft.AircraftRequestDTO;
import nl.novi.deepwrench42.dtos.aircraft.AircraftResponseDTO;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.AircraftService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/aircraft")
@Tag(name = "Aircraft Management")
public class AircraftController {

    private final AircraftService aircraftService;
    private final UrlHelper urlHelper;

    public AircraftController(AircraftService aircraftService, UrlHelper urlHelper) {
        this.aircraftService = aircraftService;
        this.urlHelper = urlHelper;
    }

    @Operation(
            description = "Get details of all aircraft",
            summary = "Get details of all aircraft"
    )
    @GetMapping
    public ResponseEntity<List<AircraftResponseDTO>> getAllAircraft() {
        List<AircraftResponseDTO> aircrafts = aircraftService.findAllAircraft();
        return ResponseEntity.ok(aircrafts);
    }

    @Operation(
            description = "Get details of a specific aircraft id",
            summary = "Get details of a specific aircraft"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponseDTO> getAircraftById(@PathVariable Long id) {
        AircraftResponseDTO aircraft = aircraftService.findAircraftById(id);
        return new ResponseEntity<AircraftResponseDTO>(aircraft, HttpStatus.OK);
    }

    @Operation(
            description = "Create a new aircraft, admin & leads only",
            summary = "Create a new aircraft"
    )
    @PostMapping
    public ResponseEntity<AircraftResponseDTO> createAircraft(@Valid @RequestBody AircraftRequestDTO aircraftInput) {
        AircraftResponseDTO newAircraft = aircraftService.createAircraft(aircraftInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newAircraft.getId())).body(newAircraft);
    }

    @Operation(
            description = "Update a specific aircraft id, admin & leads only",
            summary = "Update a specific aircraft"
    )
    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponseDTO> updateAircraft(@PathVariable Long id, @Valid @RequestBody AircraftRequestDTO aircraftInput) {
        AircraftResponseDTO updatedAircraft = aircraftService.updateAircraft(id, aircraftInput);
        return new ResponseEntity<>(updatedAircraft, HttpStatus.OK);
    }

    @Operation(
            description = "Delete a specific aircraft with id, admin only",
            summary = "Delete a specific aircraft"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
