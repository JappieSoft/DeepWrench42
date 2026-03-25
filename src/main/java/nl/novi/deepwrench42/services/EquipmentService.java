package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckInRequestDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckInResponseDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckOutRequestDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckOutResponseDTO;
import nl.novi.deepwrench42.entities.AircraftEntity;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.entities.ToolKitEntity;
import nl.novi.deepwrench42.entities.UserEntity;
import nl.novi.deepwrench42.helpers.CheckInHelper;
import nl.novi.deepwrench42.helpers.CheckOutHelper;
import nl.novi.deepwrench42.helpers.ToolExpiryHelper;
import nl.novi.deepwrench42.helpers.ToolStatusHelper;
import nl.novi.deepwrench42.repository.AircraftRepository;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import nl.novi.deepwrench42.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EquipmentService {

    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;
    private final UserRepository userRepository;
    private final AircraftRepository aircraftRepository;
    private final ToolStatusHelper toolStatusHelper;
    private final ToolExpiryHelper toolExpiryHelper;
    private final CheckOutHelper checkOutHelper;
    private final CheckInHelper checkInHelper;

    public EquipmentService(ToolRepository toolRepository,
                            ToolKitRepository toolKitRepository,
                            UserRepository userRepository,
                            AircraftRepository aircraftRepository,
                            ToolStatusHelper toolStatusHelper,
                            ToolExpiryHelper toolExpiryHelper,
                            CheckOutHelper checkOutHelper,
                            CheckInHelper checkInHelper
    ) {
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        this.userRepository = userRepository;
        this.aircraftRepository = aircraftRepository;
        this.toolStatusHelper = toolStatusHelper;
        this.toolExpiryHelper = toolExpiryHelper;
        this.checkOutHelper = checkOutHelper;
        this.checkInHelper = checkInHelper;
    }

    public EquipmentCheckOutResponseDTO checkOut(EquipmentCheckOutRequestDTO requestDTO) {
        UserEntity user = userRepository.findByEmployeeId(requestDTO.getEmployeeNumber())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        AircraftEntity aircraft = aircraftRepository.findByShipNumber(requestDTO.getAircraft())
                .orElseThrow(() -> new IllegalArgumentException("Aircraft not found"));

        // Tool
        Optional<ToolEntity> selectedTool = toolRepository.findByItemId(requestDTO.getEquipmentItemId());
        if (selectedTool.isPresent()) {
            ToolEntity tool = selectedTool.get();
            toolExpiryHelper.checkDueDateTool(tool);
            toolStatusHelper.validateForCheckOut(tool);
            return checkOutHelper.performToolCheckOut(tool, user, requestDTO);
        }

        // ToolKit
        Optional<ToolKitEntity> selectedToolKit = toolKitRepository.findByItemId(requestDTO.getEquipmentItemId());
        if (selectedToolKit.isPresent()) {
            ToolKitEntity kit = selectedToolKit.get();
            toolExpiryHelper.checkDueDateToolKit(kit);
            toolStatusHelper.validateForCheckOut(kit);
            return checkOutHelper.performToolKitCheckOut(kit, user, requestDTO);
        }

        throw new IllegalArgumentException("No tool or toolkit found with itemId: " + requestDTO.getEquipmentItemId());
    }

    public EquipmentCheckInResponseDTO checkIn(EquipmentCheckInRequestDTO requestDTO) {
        UserEntity user = userRepository.findByEmployeeId(requestDTO.getEmployeeNumber())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        AircraftEntity aircraft = aircraftRepository.findByShipNumber(requestDTO.getAircraft())
                .orElseThrow(() -> new IllegalArgumentException("Aircraft not found"));

        // Tool
        Optional<ToolEntity> selectedTool = toolRepository.findByItemId(requestDTO.getEquipmentItemId());
        if (selectedTool.isPresent()) {
            ToolEntity tool = selectedTool.get();
            toolStatusHelper.validateForCheckIn(tool);
            return checkInHelper.performToolCheckIn(tool, user, requestDTO);
        }

        // ToolKit
        Optional<ToolKitEntity> selectedToolKit = toolKitRepository.findByItemId(requestDTO.getEquipmentItemId());
        if (selectedToolKit.isPresent()) {
            ToolKitEntity kit = selectedToolKit.get();
            toolStatusHelper.validateForCheckIn(kit);
            return checkInHelper.performToolKitCheckIn(kit, user, requestDTO);
        }

        throw new IllegalArgumentException("No tool or toolkit found with itemId: " + requestDTO.getEquipmentItemId());
    }
}
