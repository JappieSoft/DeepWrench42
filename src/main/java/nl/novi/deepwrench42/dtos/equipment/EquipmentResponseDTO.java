package nl.novi.deepwrench42.dtos.equipment;

import nl.novi.deepwrench42.dtos.aircraft.AircraftResponseDTO;
import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationResponseDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.entities.*;

import java.time.LocalDateTime;

public class EquipmentResponseDTO {
    private Long id;
    private EquipmentType equipmentType;
    private String itemId;
    private String name;
    private String pictureFileName;
    private StorageLocationResponseDTO storageLocation;
    private EquipmentStatus status;
    private UserResponseDTO checkedOutBy;
    private LocalDateTime checkedOutDate;
    private Boolean hasInspection;
    private String comments;

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

    public EquipmentType getEquipmentType() {   return equipmentType;   }
    public void setEquipmentType(EquipmentType equipmentType) { this.equipmentType = equipmentType; }

    public String getItemId() { return itemId;  }
    public void setItemId(String itemId) {  this.itemId = itemId;   }

    public String getName() {   return name;    }
    public void setName(String name) {  this.name = name;   }

    public String getPictureFileName() {    return pictureFileName; }
    public void setPictureFileName(String picture) {    this.pictureFileName = pictureFileName; }

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