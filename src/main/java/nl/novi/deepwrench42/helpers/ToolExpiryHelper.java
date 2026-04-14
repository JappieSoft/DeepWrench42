package nl.novi.deepwrench42.helpers;

import nl.novi.deepwrench42.entities.*;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ToolExpiryHelper {
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;

    public ToolExpiryHelper(ToolRepository toolRepository, ToolKitRepository toolKitRepository) {
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
    }

    public void checkDueDateTool(ToolEntity tool) {
        InspectionEntity inspection = tool.getInspection();
        if (inspection != null) {
            if (!inspection.getNextDueDate().isAfter(LocalDateTime.now())) {
                if (inspection.getInspectionType().equals(InspectionType.CALIBRATION)) {
                    tool.setStatus(EquipmentStatus.CALIBRATION_DUE);
                } else {
                    tool.setStatus(EquipmentStatus.MAINTENANCE_DUE);
                }
                toolRepository.save(tool);
            }
        }
    }

    public void checkDueDateToolKit(ToolKitEntity toolKit) {
        InspectionEntity inspection = toolKit.getInspection();
        if (inspection != null) {
            if (!inspection.getNextDueDate().isAfter(LocalDateTime.now())) {
                if (inspection.getInspectionType().equals(InspectionType.CALIBRATION)) {
                    toolKit.setStatus(EquipmentStatus.CALIBRATION_DUE);
                } else {
                    toolKit.setStatus(EquipmentStatus.MAINTENANCE_DUE);
                }
                toolKitRepository.save(toolKit);
            }
        }
    }
}
