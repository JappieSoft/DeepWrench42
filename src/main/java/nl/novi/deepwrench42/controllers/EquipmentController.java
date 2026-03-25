package nl.novi.deepwrench42.controllers;

import jakarta.validation.Valid;

import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckInRequestDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckInResponseDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckOutRequestDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckOutResponseDTO;
import nl.novi.deepwrench42.services.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<EquipmentCheckOutResponseDTO> checkOut(@Valid @RequestBody EquipmentCheckOutRequestDTO actionInput) {
        EquipmentCheckOutResponseDTO newAction = equipmentService.checkOut(actionInput);
        return ResponseEntity.ok(newAction);
    }

    @PostMapping("/checkin")
    public ResponseEntity<EquipmentCheckInResponseDTO> checkIn(@Valid @RequestBody EquipmentCheckInRequestDTO actionInput) {
        EquipmentCheckInResponseDTO newAction = equipmentService.checkIn(actionInput);
        return ResponseEntity.ok(newAction);
    }

}
