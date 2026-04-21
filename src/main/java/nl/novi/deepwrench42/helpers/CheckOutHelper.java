package nl.novi.deepwrench42.helpers;

import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckOutRequestDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckOutResponseDTO;

import nl.novi.deepwrench42.entities.*;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolLogRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CheckOutHelper {
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;
    private final ToolLogRepository toolLogRepository;

    public CheckOutHelper(ToolRepository toolRepository,
                          ToolKitRepository toolKitRepository,
                          ToolLogRepository toolLogRepository
    ) {
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
        this.toolLogRepository = toolLogRepository;
    }

    public EquipmentCheckOutResponseDTO performToolCheckOut(
            ToolEntity tool,
            UserEntity user,
            EquipmentCheckOutRequestDTO dto
    ) {
        tool.setStatus(EquipmentStatus.CHECKED_OUT);
        tool.setCheckedOutBy(user);
        tool.setCheckedOutDate(LocalDateTime.now());
        toolRepository.save(tool);

        ToolLogEntity log = createToolLog(user.getEmployeeId(), tool, dto);
        toolLogRepository.save(log);

        return mapToCheckOutDto(log, tool);
    }

    public EquipmentCheckOutResponseDTO performToolKitCheckOut(
            ToolKitEntity toolKit,
            UserEntity user,
            EquipmentCheckOutRequestDTO dto
    ) {
        toolKit.setStatus(EquipmentStatus.CHECKED_OUT);
        toolKit.setCheckedOutBy(user);
        toolKit.setCheckedOutDate(LocalDateTime.now());
        toolKitRepository.save(toolKit);

        ToolLogEntity log = createToolKitLog(user.getEmployeeId(), toolKit, dto);
        toolLogRepository.save(log);

        return mapToCheckOutDtoKit(log, toolKit);
    }

    private ToolLogEntity createToolLog(String empNumber, ToolEntity tool, EquipmentCheckOutRequestDTO requestDto) {
        ToolLogEntity log = new ToolLogEntity();
        log.setTimeStamp(LocalDateTime.now());
        log.setActionType(ToolLogActionType.valueOf("CHECK_OUT"));
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

    private ToolLogEntity createToolKitLog(String empNumber, ToolKitEntity toolKit, EquipmentCheckOutRequestDTO requestDto) {
        ToolLogEntity log = new ToolLogEntity();
        log.setTimeStamp(LocalDateTime.now());
        log.setActionType(ToolLogActionType.valueOf("CHECK_OUT"));
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

    private EquipmentCheckOutResponseDTO mapToCheckOutDto(
            ToolLogEntity log, ToolEntity tool) {
        var dto = new EquipmentCheckOutResponseDTO();
        dto.setToolLogId(log.getId());
        dto.setEquipmentItemId(log.getItemNumber());
        dto.setStatus(tool.getStatus());
        dto.setCheckedOutBy(log.getActionBy());
        dto.setCheckedOutDate(tool.getCheckedOutDate());
        dto.setAircraft(log.getAircraftNumber());
        dto.setComments(log.getComments());
        return dto;
    }

    private EquipmentCheckOutResponseDTO mapToCheckOutDtoKit(
            ToolLogEntity log, ToolKitEntity tooLKit) {
        var dto = new EquipmentCheckOutResponseDTO();
        dto.setToolLogId(log.getId());
        dto.setEquipmentItemId(log.getItemNumber());
        dto.setStatus(tooLKit.getStatus());
        dto.setCheckedOutBy(log.getActionBy());
        dto.setCheckedOutDate(tooLKit.getCheckedOutDate());
        dto.setAircraft(log.getAircraftNumber());
        dto.setComments(log.getComments());
        return dto;
    }
}
