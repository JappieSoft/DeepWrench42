package nl.novi.deepwrench42.dtos.equipment;

import nl.novi.deepwrench42.entities.EquipmentStatus;

import java.time.LocalDateTime;

public class EquipmentCheckInResponseDTO {
    private Long toolLogId;
    private String equipmentItemId;
    private EquipmentStatus status;
    private String checkedInBy;
    private LocalDateTime checkedInDate;
    private String storageLocation;
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

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getCheckedInBy() {
        return checkedInBy;
    }

    public void setCheckedInBy(String checkedInBy) {
        this.checkedInBy = checkedInBy;
    }

    public LocalDateTime getCheckedInDate() {
        return checkedInDate;
    }

    public void setCheckedInDate(LocalDateTime checkedInDate) {
        this.checkedInDate = checkedInDate;
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
