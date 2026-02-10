package nl.novi.deepwrench42.controllers;

import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationRequestDTO;
import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationResponseDTO;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.StorageLocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/storage-location")
public class StorageLocationController {

    private final StorageLocationService storageLocationService;
    private final UrlHelper urlHelper;

    public StorageLocationController(StorageLocationService storageLocationService, UrlHelper urlHelper) {
        this.storageLocationService = storageLocationService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<StorageLocationResponseDTO>> getAllStorageLocations() {
        List<StorageLocationResponseDTO> storageLocations = storageLocationService.findAllStorageLocations();
        return ResponseEntity.ok(storageLocations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageLocationResponseDTO> getStorageLocationById(@PathVariable Long id) {
        StorageLocationResponseDTO storageLocation = storageLocationService.findStorageLocationById(id);
        if (storageLocation == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<StorageLocationResponseDTO>(storageLocation, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StorageLocationResponseDTO> createStorageLocation(@Valid @RequestBody StorageLocationRequestDTO storageLocationInput) {
        StorageLocationResponseDTO newStorageLocation = storageLocationService.createStorageLocation(storageLocationInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newStorageLocation.getId())).body(newStorageLocation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageLocationResponseDTO> updateStorageLocation(@PathVariable Long id, @Valid @RequestBody StorageLocationRequestDTO storageLocationInput) {
        StorageLocationResponseDTO updatedStorageLocation = storageLocationService.updateStorageLocation(id, storageLocationInput);
        return new ResponseEntity<>(updatedStorageLocation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStorageLocation(@PathVariable Long id) {
        storageLocationService.deleteStorageLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
