package nl.novi.deepwrench42.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Storage Location Management")
public class StorageLocationController {

    private final StorageLocationService storageLocationService;
    private final UrlHelper urlHelper;

    public StorageLocationController(StorageLocationService storageLocationService, UrlHelper urlHelper) {
        this.storageLocationService = storageLocationService;
        this.urlHelper = urlHelper;
    }

    @Operation(
            description = "Get details of all storage locations",
            summary = "Get details of all storage locations"
    )
    @GetMapping
    public ResponseEntity<List<StorageLocationResponseDTO>> getAllStorageLocations() {
        List<StorageLocationResponseDTO> storageLocations = storageLocationService.findAllStorageLocations();
        return ResponseEntity.ok(storageLocations);
    }

    @Operation(
            description = "Get details of a specific storage location id",
            summary = "Get details of a specific storage location"
    )
    @GetMapping("/{id}")
    public ResponseEntity<StorageLocationResponseDTO> getStorageLocationById(@PathVariable Long id) {
        StorageLocationResponseDTO storageLocation = storageLocationService.findStorageLocationById(id);
        return new ResponseEntity<StorageLocationResponseDTO>(storageLocation, HttpStatus.OK);
    }

    @Operation(
            description = "Create a new storage location, admin & leads only",
            summary = "Create a new storage location"
    )
    @PostMapping
    public ResponseEntity<StorageLocationResponseDTO> createStorageLocation(@Valid @RequestBody StorageLocationRequestDTO storageLocationInput) {
        StorageLocationResponseDTO newStorageLocation = storageLocationService.createStorageLocation(storageLocationInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newStorageLocation.getId())).body(newStorageLocation);
    }

    @Operation(
            description = "Update a specific storage location id, admin & leads only",
            summary = "Update a specific storage location"
    )
    @PutMapping("/{id}")
    public ResponseEntity<StorageLocationResponseDTO> updateStorageLocation(@PathVariable Long id, @Valid @RequestBody StorageLocationRequestDTO storageLocationInput) {
        StorageLocationResponseDTO updatedStorageLocation = storageLocationService.updateStorageLocation(id, storageLocationInput);
        return new ResponseEntity<>(updatedStorageLocation, HttpStatus.OK);
    }

    @Operation(
            description = "Delete a specific storage location with id, admin only",
            summary = "Delete a specific storage location"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStorageLocation(@PathVariable Long id) {
        storageLocationService.deleteStorageLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
