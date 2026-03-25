package nl.novi.deepwrench42.helpers;

import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckInRequestDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckInResponseDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckOutRequestDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckOutResponseDTO;
import static nl.novi.deepwrench42.entities.EquipmentStatus.*;

import nl.novi.deepwrench42.entities.*;
import nl.novi.deepwrench42.repository.StorageLocationRepository;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolLogRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;



@Service
public class CheckInHelper {

    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;
    private final ToolLogRepository toolLogRepository;

    public CheckInHelper(ToolRepository toolRepository,
                          ToolKitRepository toolKitRepository,
                          ToolLogRepository toolLogRepository
    ) {
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        this.toolLogRepository = toolLogRepository;
    }

    public EquipmentCheckInResponseDTO performToolCheckIn(ToolEntity tool, UserEntity user, EquipmentCheckInRequestDTO requestDTO) {

        InspectionEntity inspection = tool.getInspection();

        if (requestDTO.getMissing() == true){tool.setStatus(MISSING);
        } else if (requestDTO.getProblem() == true) {tool.setStatus(UNSERVICEABLE);
        } else if (inspection != null) {
            if (!inspection.getNextDueDate().isAfter(LocalDateTime.now())) {
                if (inspection.getInspectionType().equals(InspectionType.CALIBRATION)) {
                    tool.setStatus(EquipmentStatus.CALIBRATION_DUE);
                } else {tool.setStatus(EquipmentStatus.MAINTENANCE_DUE);}}
        } else {tool.setStatus(CHECKED_IN);}

        tool.setCheckedOutBy(null);
        tool.setCheckedOutDate(null);
        toolRepository.save(tool);

        ToolLogEntity log = createToolLog(user.getEmployeeId(), tool, requestDTO);
        toolLogRepository.save(log);

        return mapToCheckInDto(log, tool);
    }

    public EquipmentCheckInResponseDTO performToolKitCheckIn(ToolKitEntity toolKit, UserEntity user, EquipmentCheckInRequestDTO requestDTO) {

        InspectionEntity inspection = toolKit.getInspection();

        if (requestDTO.getMissing() == true){toolKit.setStatus(MISSING);
        } else if (requestDTO.getProblem() == true) {toolKit.setStatus(UNSERVICEABLE);
        } else if (inspection != null) {
                if (!inspection.getNextDueDate().isAfter(LocalDateTime.now())) {
                    if (inspection.getInspectionType().equals(InspectionType.CALIBRATION)) {
                        toolKit.setStatus(EquipmentStatus.CALIBRATION_DUE);
                    } else {toolKit.setStatus(EquipmentStatus.MAINTENANCE_DUE);}}
        } else {toolKit.setStatus(CHECKED_IN);}

        toolKit.setCheckedOutBy(null);
        toolKit.setCheckedOutDate(null);
        toolKitRepository.save(toolKit);

        ToolLogEntity log = createToolKitLog(user.getEmployeeId(), toolKit, requestDTO);
        toolLogRepository.save(log);

        return mapToCheckInDtoKit(log, toolKit);
    }

    private ToolLogEntity createToolLog(String empNumber, ToolEntity tool, EquipmentCheckInRequestDTO requestDto) {
        ToolLogEntity log = new ToolLogEntity();
        log.setTimeStamp(LocalDateTime.now());
        log.setActionType(ToolLogActionType.valueOf("CHECK_IN"));
        log.setActionResult(tool.getStatus());
        log.setActionBy(empNumber);
        log.setItemNumber(requestDto.getEquipmentItemId());
        log.setItemType("TOOL");
        log.setItemName(tool.getName());
        log.setAtaCode(tool.getAtaCode());
        log.setPartNumber(tool.getPartNumber());
        log.setSerialNumber(tool.getSerialNumber());
        log.setManufacturer(tool.getManufacturer());
        log.setAircraftNumber(requestDto.getAircraft());
        log.setComments(requestDto.getComments());
        return log;
    }

    private ToolLogEntity createToolKitLog(String empNumber, ToolKitEntity toolKit, EquipmentCheckInRequestDTO requestDto) {
        ToolLogEntity log = new ToolLogEntity();
        log.setTimeStamp(LocalDateTime.now());
        log.setActionType(ToolLogActionType.valueOf("CHECK_IN"));
        log.setActionResult(toolKit.getStatus());
        log.setActionBy(empNumber);
        log.setItemNumber(requestDto.getEquipmentItemId());
        log.setItemType("TOOLKIT");
        log.setItemName(toolKit.getName());
        log.setAtaCode(toolKit.getAtaCode());
        log.setPartNumber(toolKit.getPartNumber());
        log.setSerialNumber(toolKit.getSerialNumber());
        log.setManufacturer(toolKit.getManufacturer());
        log.setAircraftNumber(requestDto.getAircraft());
        log.setComments(requestDto.getComments());
        return log;
    }

    private EquipmentCheckInResponseDTO mapToCheckInDto(
            ToolLogEntity log, ToolEntity tool) {
        var dto = new EquipmentCheckInResponseDTO();
        dto.setToolLogId(log.getId());
        dto.setEquipmentItemId(log.getItemNumber());
        dto.setStatus(tool.getStatus());
        dto.setCheckedInBy(log.getActionBy());
        dto.setCheckedInDate(log.getTimeStamp());
        dto.setStorageLocation(
                tool.getStorageLocation().getId() != null
                        ? toolRepository.findStorageLocationString(tool.getStorageLocation().getId())
                        : null
        );
        dto.setAircraft(log.getAircraftNumber());
        dto.setComments(log.getComments());
        return dto;
    }

    private EquipmentCheckInResponseDTO mapToCheckInDtoKit(
            ToolLogEntity log, ToolKitEntity tooLKit) {
        var dto = new EquipmentCheckInResponseDTO();
        dto.setToolLogId(log.getId());
        dto.setEquipmentItemId(log.getItemNumber());
        dto.setStatus(tooLKit.getStatus());
        dto.setCheckedInBy(log.getActionBy());
        dto.setCheckedInDate(log.getTimeStamp());
        dto.setStorageLocation(
                tooLKit.getStorageLocation().getId() != null
                        ? toolKitRepository.findStorageLocationString(tooLKit.getStorageLocation().getId())
                        : null
        );
        dto.setAircraft(log.getAircraftNumber());
        dto.setComments(log.getComments());
        return dto;
    }
}