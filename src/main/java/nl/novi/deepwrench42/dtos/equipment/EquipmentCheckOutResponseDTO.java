package nl.novi.deepwrench42.dtos.equipment;

import nl.novi.deepwrench42.entities.EquipmentStatus;

import java.time.LocalDateTime;

public class EquipmentCheckOutResponseDTO {
    private Long toolLogId;
    private String equipmentItemId;
    private EquipmentStatus status;
    private String checkedOutBy;
    private LocalDateTime checkedOutDate;
    private String aircraft;
    private String comments;

    // Getters en Setters
    public Long getToolLogId() {
        return toolLogId;
    }

    public void setToolLogId(Long toolLogId) {
        this.toolLogId = toolLogId;
    }

    public String getEquipmentItemId() {
        return equipmentItemId;
    }

    public void setEquipmentItemId(String equipmentItemId) {
        this.equipmentItemId = equipmentItemId;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    public String getCheckedOutBy() {
        return checkedOutBy;
    }

    public void setCheckedOutBy(String checkedOutBy) {
        this.checkedOutBy = checkedOutBy;
    }

    public LocalDateTime getCheckedOutDate() {
        return checkedOutDate;
    }

    public void setCheckedOutDate(LocalDateTime checkedOutDate) {
        this.checkedOutDate = checkedOutDate;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
