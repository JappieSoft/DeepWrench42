package nl.novi.deepwrench42.dtos.inspection;

import jakarta.validation.constraints.*;
import nl.novi.deepwrench42.entities.InspectionType;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.entities.ToolKitEntity;

import java.time.LocalDateTime;

public class InspectionRequestDTO {
    @NotNull(message = "Inspection date is required")
    private LocalDateTime inspectionDate;
    @NotNull(message = "Inspection type is required")
    private InspectionType inspectionType;
    @NotNull(message = "Result of last inspection is required")
    private Boolean inspectionPassed;
    private String comments;
    @NotNull(message = "Next due date is required")
    @Future(message = "Next due date must be in the future")
    private LocalDateTime nextDueDate;
    @NotNull(message = "Inspection interval is required")
    @Min(value = 1, message = "Interval must be positive")
    private Integer inspectionInterval;
    private Long toolId;
    private Long toolKitId;

    // Getters en Setters
    public LocalDateTime getInspectionDate() {  return inspectionDate;  }
    public void setInspectionDate(LocalDateTime inspectionDate) {   this.inspectionDate = inspectionDate;   }

    public InspectionType getInspectionType() {   return inspectionType;  }
    public void setInspectionType(InspectionType inspectionType) {    this.inspectionType = inspectionType;   }

    public Boolean getInspectionPassed() {  return inspectionPassed;   }
    public void setInspectionPassed(Boolean inspectionPassed) {   this.inspectionPassed = inspectionPassed; }

    public String getComments() { return comments;  }
    public void setComments(String comments) {  this.comments = comments;   }

    public LocalDateTime getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(LocalDateTime nextDueDate) { this.nextDueDate = nextDueDate; }

    public Integer getInspectionInterval() {    return inspectionInterval;  }
    public void setInspectionInterval(Integer inspectionInterval) { this.inspectionInterval = inspectionInterval;   }

    public Long getToolId() {   return toolId;    }
    public void setToolId(Long toolId) {  this.toolId = toolId;   }

    public Long getToolKitId() { return toolKitId; }
    public void setToolKitId(Long toolKitId) { this.toolKitId = toolKitId; }
}