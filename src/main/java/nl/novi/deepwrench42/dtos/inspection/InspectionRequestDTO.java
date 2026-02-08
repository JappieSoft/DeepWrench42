package nl.novi.deepwrench42.dtos.inspection;

import jakarta.validation.constraints.*;
import nl.novi.deepwrench42.entities.InspectionType;

import java.time.LocalDateTime;

public class InspectionRequestDTO {
    @NotNull(message = "Inspection date is required")
    private LocalDateTime inspectionDate;
    @NotNull(message = "Inspection type is required")
    private String inspectionType;
    @NotNull(message = "Next due date is required")
    @Future(message = "Next due date must be in the future")
    private LocalDateTime nextDueDate;
    @NotNull(message = "Inspection interval is required")
    @Min(value = 1, message = "Interval must be positive")
    private int inspectionInterval;
    @NotNull(message = "Tool id is required")
    @Positive(message = "Tool id must be positive")
    private Long toolId;

    // Getters en Setters
    public LocalDateTime getInspectionDate() {  return inspectionDate;  }
    public void setInspectionDate(LocalDateTime inspectionDate) {   this.inspectionDate = inspectionDate;   }

    public String getInspectionType() {   return inspectionType;  }
    public void setInspectionType(String inspectionType) {    this.inspectionType = inspectionType;   }

    public LocalDateTime getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(LocalDateTime nextDueDate) { this.nextDueDate = nextDueDate; }

    public int getInspectionInterval() {    return inspectionInterval;  }
    public void setInspectionInterval(int inspectionInterval) { this.inspectionInterval = inspectionInterval;   }

    public Long getToolId() {   return toolId;  }
    public void setToolId(Long toolId) {    this.toolId = toolId;   }
}