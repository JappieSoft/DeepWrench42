package nl.novi.deepwrench42.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.inspection.CompleteInspectionDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionRequestDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionResponseDTO;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.InspectionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/inspection")
@Tag(name = "Inspection Management")
public class InspectionController {

    private final InspectionService inspectionService;
    private final UrlHelper urlHelper;

    public InspectionController(InspectionService inspectionService, UrlHelper urlHelper) {
        this.inspectionService = inspectionService;
        this.urlHelper = urlHelper;
    }

    @Operation(
            description = "Get details of all inspections, admin & leads only",
            summary = "Get details of all inspections"
    )
    @GetMapping
    public ResponseEntity<List<InspectionResponseDTO>> getAllInspections() {
        List<InspectionResponseDTO> inspections = inspectionService.getAllInspections();
        return ResponseEntity.ok(inspections);
    }

    @Operation(
            description = "Get details of a specific inspection id",
            summary = "Get details of a specific inspection"
    )
    @GetMapping("/{id}")
    public ResponseEntity<InspectionResponseDTO> getInspectionById(@PathVariable Long id) {
        InspectionResponseDTO inspection = inspectionService.findInspectionById(id);
        return new ResponseEntity<InspectionResponseDTO>(inspection, HttpStatus.OK);
    }

    @Operation(
            description = "Create a new inspection, admin & leads only",
            summary = "Create a new inspection"
    )
    @PostMapping
    public ResponseEntity<InspectionResponseDTO> createInspection(@Valid @RequestBody InspectionRequestDTO inspectionInput) {
        InspectionResponseDTO newInspection = inspectionService.createInspection(inspectionInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newInspection.getId())).body(newInspection);
    }

    @Operation(
            description = "Update a specific inspection id, admin & leads only",
            summary = "Update a specific inspection"
    )
    @PutMapping("/{id}")
    public ResponseEntity<InspectionResponseDTO> updateInspection(@PathVariable Long id, @Valid @RequestBody InspectionRequestDTO inspectionInput) {
        InspectionResponseDTO updatedInspection = inspectionService.updateInspection(id, inspectionInput);
        return new ResponseEntity<>(updatedInspection, HttpStatus.OK);
    }

    @Operation(
            description = "Delete a specific inspection with id, admin only",
            summary = "Delete a specific inspection"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspection(@PathVariable Long id) {
        inspectionService.deleteInspection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            description = "Complete inspection and enter/update all time relevant limits, admin & leads only",
            summary = "Perform a specific inspection"
    )
    @PostMapping("/performed")
    public ResponseEntity<InspectionResponseDTO> performedInspection(@Valid @RequestBody CompleteInspectionDTO completedInspection) {
        InspectionResponseDTO result = inspectionService.completeInspection(completedInspection);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(
            description = "Find inspection with date completed before submitted date, admin & leads only",
            summary = "Find completed inspection before date"
    )
    @GetMapping("/find-inspection-date-before")
    public ResponseEntity<List<InspectionResponseDTO>> findByInspectionDateBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<InspectionResponseDTO> inspections = inspectionService.findByInspectionDateBefore(date);
        return ResponseEntity.ok(inspections);
    }

    @Operation(
            description = "Find inspection completed with date after submitted date, admin & leads only",
            summary = "Find completed inspection after date"
    )
    @GetMapping("/find-inspection-date-after")
    public ResponseEntity<List<InspectionResponseDTO>> findByInspectionDateAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<InspectionResponseDTO> inspections = inspectionService.findByInspectionDateAfter(date);
        return ResponseEntity.ok(inspections);
    }

    @Operation(
            description = "Find inspections that will be due at submitted date, admin & leads only",
            summary = "Find inspection due by date"
    )
    @GetMapping("/find-next-overdue")
    public ResponseEntity<List<InspectionResponseDTO>> findOverdueByNextDueDateBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<InspectionResponseDTO> inspections = inspectionService.findOverdueByNextDueDateBefore(date);
        return ResponseEntity.ok(inspections);
    }
}


