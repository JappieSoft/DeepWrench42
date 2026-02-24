package nl.novi.deepwrench42.dtos.equipment;


import jakarta.validation.constraints.*;
import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationResponseDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
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
    private StorageLocationResponseDTO storageLocation;

    @NotNull(message = "Equipment status is required")
    private EquipmentStatus status;
    private UserResponseDTO checkedOutBy;
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

    public StorageLocationResponseDTO getStorageLocation() { return storageLocation; }
    public void setStorageLocation(StorageLocationResponseDTO storageLocation) { this.storageLocation = storageLocation; }

    public EquipmentStatus getStatus() {    return status;  }
    public void setStatus(EquipmentStatus status) { this.status = status;   }

    public UserResponseDTO getCheckedOutBy() {   return checkedOutBy;    }
    public void setCheckedOutBy(UserResponseDTO checkedOutBy) {  this.checkedOutBy = checkedOutBy;   }

    public LocalDateTime getCheckedOutDate() {return checkedOutDate;  }
    public void setCheckedOutDate(LocalDateTime checkedOutDate) {   this.checkedOutDate = checkedOutDate;   }

    public Boolean getHasInspection() {  return hasInspection;   }
    public void setHasInspection(Boolean hasInspection) {   this.hasInspection = hasInspection; }

    public String getComments() {   return comments;    }
    public void setComments(String comments) {  this.comments = comments;   }
}
