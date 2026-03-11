package nl.novi.deepwrench42.dtos.equipment;


import jakarta.validation.constraints.*;
import nl.novi.deepwrench42.entities.*;

import java.time.LocalDateTime;

public class EquipmentRequestDTO {
    @NotNull(message = "Equipment type is required")
    private EquipmentType equipmentType;

    @NotNull(message = "Item ID is required")
    private String itemId;

    @NotNull(message = "Name is required")
    private String name;
    private String picture;
    private Long storageLocation;

    @NotNull(message = "Equipment status is required")
    private String status;
    private Long checkedOutBy;
    private LocalDateTime checkedOutDate;
    private Boolean hasInspection;
    private String comments;

    // Getters en Setters
    public EquipmentType getEquipmentType() {   return equipmentType;   }
    public void setEquipmentType(EquipmentType equipmentType) { this.equipmentType = equipmentType; }

    public String getItemId() { return itemId;  }
    public void setItemId(String itemId) {  this.itemId = itemId;   }

    public String getName() {   return name;    }
    public void setName(String name) {  this.name = name;   }

    public String getPicture() {    return picture; }
    public void setPicture(String picture) {    this.picture = picture; }

    public Long getStorageLocation() { return storageLocation; }
    public void setStorageLocation(Long storageLocation) { this.storageLocation = storageLocation; }

    public String getStatus() {    return status;  }
    public void setStatus(String status) { this.status = status;   }

    public Long getCheckedOutBy() {   return checkedOutBy;    }
    public void setCheckedOutBy(Long checkedOutBy) {  this.checkedOutBy = checkedOutBy;   }

    public LocalDateTime getCheckedOutDate() {return checkedOutDate;  }
    public void setCheckedOutDate(LocalDateTime checkedOutDate) {   this.checkedOutDate = checkedOutDate;   }

    public Boolean getHasInspection() {  return hasInspection;   }
    public void setHasInspection(Boolean hasInspection) {   this.hasInspection = hasInspection; }

    public String getComments() {   return comments;    }
    public void setComments(String comments) {  this.comments = comments;   }
}

