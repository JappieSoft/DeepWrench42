package nl.novi.deepwrench42.controllers;

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
public class AircraftTypeController {

    private final AircraftTypeService aircraftTypeService;
    private final UrlHelper urlHelper;
    
    public AircraftTypeController(AircraftTypeService aircraftTypeService, UrlHelper urlHelper) {
        this.aircraftTypeService = aircraftTypeService;
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
        if (aircraftType == null) {
            return ResponseEntity.notFound().build();
        }
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
        aircraftTypeService.deleteAircraftType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
