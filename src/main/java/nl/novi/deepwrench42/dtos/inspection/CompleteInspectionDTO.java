package nl.novi.deepwrench42.dtos.inspection;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CompleteInspectionDTO {
    @NotNull(message = "Equipment item Id is required")
    private String equipmentItemId;
    @NotNull(message = "Inspection date is required")
    private LocalDateTime inspectionDate;
    @NotNull(message = "Result of last inspection is required")
    private Boolean inspectionPassed;
    private String comments;
    @NotNull(message = "Next due date is required")
    @Future(message = "Next due date must be in the future")
    private LocalDateTime nextDueDate;

    // Getters en Setters
    public String getEquipmentItemId() {    return equipmentItemId; }
    public void setEquipmentItemId(String equipmentItemId) {    this.equipmentItemId = equipmentItemId; }

    public LocalDateTime getInspectionDate() {  return inspectionDate;  }
    public void setInspectionDate(LocalDateTime inspectionDate) {   this.inspectionDate = inspectionDate;   }

    public Boolean getInspectionPassed() {  return inspectionPassed;   }
    public void setInspectionPassed(Boolean inspectionPassed) {   this.inspectionPassed = inspectionPassed; }

    public String getComments() { return comments;  }
    public void setComments(String comments) {  this.comments = comments;   }

    public LocalDateTime getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(LocalDateTime nextDueDate) { this.nextDueDate = nextDueDate; }
}