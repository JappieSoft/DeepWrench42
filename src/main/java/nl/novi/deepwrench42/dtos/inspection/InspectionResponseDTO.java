package nl.novi.deepwrench42.dtos.inspection;

import nl.novi.deepwrench42.entities.InspectionType;


import java.time.LocalDateTime;

public class InspectionResponseDTO {
    private Long id;
    private LocalDateTime inspectionDate;
    private InspectionType inspectionType;
    private Boolean inspectionPassed;
    private String comments;
    private LocalDateTime nextDueDate;
    private Integer inspectionInterval;
    private Long toolId;
    private Long toolKitId;
    private String equipmentItemId;

    // Getters en Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDateTime inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public InspectionType getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(InspectionType inspectionType) {
        this.inspectionType = inspectionType;
    }

    public Boolean getInspectionPassed() {
        return inspectionPassed;
    }

    public void setInspectionPassed(Boolean inspectionPassed) {
        this.inspectionPassed = inspectionPassed;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDateTime nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public Integer getInspectionInterval() {
        return inspectionInterval;
    }

    public void setInspectionInterval(Integer inspectionInterval) {
        this.inspectionInterval = inspectionInterval;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public Long getToolKitId() {
        return toolKitId;
    }

    public void setToolKitId(Long toolKitId) {
        this.toolKitId = toolKitId;
    }

    public String getEquipmentItemId() {
        return equipmentItemId;
    }

    public void setEquipmentItemId(String equipmentItemId) {
        this.equipmentItemId = equipmentItemId;
    }
}