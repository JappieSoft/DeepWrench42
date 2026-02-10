package nl.novi.deepwrench42.controllers;

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
public class AircraftController {

    private final AircraftService aircraftService;
    private final UrlHelper urlHelper;

    public AircraftController(AircraftService aircraftService, UrlHelper urlHelper) {
        this.aircraftService = aircraftService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<AircraftResponseDTO>> getAllAircrafts() {
        List<AircraftResponseDTO> aircrafts = aircraftService.findAllAircraft();
        return ResponseEntity.ok(aircrafts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponseDTO> getAircraftById(@PathVariable Long id) {
        AircraftResponseDTO aircraft = aircraftService.findAircraftById(id);
        if (aircraft == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<AircraftResponseDTO>(aircraft, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AircraftResponseDTO> createAircraft(@Valid @RequestBody AircraftRequestDTO aircraftInput) {
        AircraftResponseDTO newAircraft = aircraftService.createAircraft(aircraftInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newAircraft.getId())).body(newAircraft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponseDTO> updateAircraft(@PathVariable Long id, @Valid @RequestBody AircraftRequestDTO aircraftInput) {
        AircraftResponseDTO updatedAircraft = aircraftService.updateAircraft(id, aircraftInput);
        return new ResponseEntity<>(updatedAircraft, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
