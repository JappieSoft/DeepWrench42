package nl.novi.deepwrench42.controllers;

import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.equipment.EquipmentResponseDTO;
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
public class InspectionController {

    private final InspectionService inspectionService;
    private final UrlHelper urlHelper;

    public InspectionController(InspectionService inspectionService, UrlHelper urlHelper) {
        this.inspectionService = inspectionService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<InspectionResponseDTO>> getAllInspections() {
        List<InspectionResponseDTO> inspections = inspectionService.getAllInspections();
        return ResponseEntity.ok(inspections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspectionResponseDTO> getInspectionById(@PathVariable Long id) {
        InspectionResponseDTO inspection = inspectionService.findInspectionById(id);
        return new ResponseEntity<InspectionResponseDTO>(inspection, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InspectionResponseDTO> createInspection(@Valid @RequestBody InspectionRequestDTO inspectionInput) {
        InspectionResponseDTO newInspection = inspectionService.createInspection(inspectionInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newInspection.getId())).body(newInspection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InspectionResponseDTO> updateInspection(@PathVariable Long id, @Valid @RequestBody InspectionRequestDTO inspectionInput) {
        InspectionResponseDTO updatedInspection = inspectionService.updateInspection(id, inspectionInput);
        return new ResponseEntity<>(updatedInspection, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspection(@PathVariable Long id) {
        inspectionService.deleteInspection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/performed")
    public ResponseEntity<InspectionResponseDTO> performedInspection( @Valid @RequestBody CompleteInspectionDTO completedInspection) {
        InspectionResponseDTO result = inspectionService.completeInspection(completedInspection);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/find-inspection-date-before")
    public ResponseEntity<List<InspectionResponseDTO>> findByInspectionDateBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<InspectionResponseDTO> inspections = inspectionService.findByInspectionDateBefore(date);
        return ResponseEntity.ok(inspections);
    }

    @GetMapping("/find-inspection-date-after")
    public ResponseEntity<List<InspectionResponseDTO>> findByInspectionDateAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<InspectionResponseDTO> inspections = inspectionService.findByInspectionDateAfter(date);
        return ResponseEntity.ok(inspections);
    }

    @GetMapping("/find-next-overdue")
    public ResponseEntity<List<InspectionResponseDTO>> findOverdueByNextDueDateBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<InspectionResponseDTO> inspections = inspectionService.findOverdueByNextDueDateBefore(date);
        return ResponseEntity.ok(inspections);
    }
}


