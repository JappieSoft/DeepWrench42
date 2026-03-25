package nl.novi.deepwrench42.dtos.toolLog;

import nl.novi.deepwrench42.dtos.aircraft.AircraftResponseDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.entities.EquipmentStatus;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.entities.ToolKitEntity;

import java.time.LocalDateTime;

public class ToolLogResponseDTO {
    private Long id;
    private LocalDateTime timeStamp;
    private String actionType;
    private String actionResult;
    private String actionBy;
    private String itemNumber;
    private String itemType;
    private String itemName;
    private Integer ataCode;
    private String partNumber;
    private String serialNumber;
    private String manufacturer;
    private String aircraftNumber;
    private String comments;

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

    public LocalDateTime getTimeStamp() {   return timeStamp;   }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }

    public String getActionType() { return actionType;  }
    public void setActionType(String actionType) {  this.actionType = actionType;   }

    public String getActionResult() { return actionResult;  }
    public void setActionResult(String actionResult) {  this.actionResult = actionResult;   }

    public String getActionBy() {   return actionBy;    }
    public void setActionBy(String actionBy) {  this.actionBy = actionBy;   }

    public String getItemNumber() { return itemNumber;  }
    public void setItemNumber(String itemNumber) {  this.itemNumber = itemNumber;   }

    public String getItemType() {   return itemType;    }
    public void setItemType(String itemType) {  this.itemType = itemType;   }

    public String getItemName() {   return itemName;    }
    public void setItemName(String itemName) {  this.itemName = itemName;   }

    public Integer getAtaCode() {   return ataCode; }
    public void setAtaCode(Integer ataCode) {   this.ataCode = ataCode; }

    public String getPartNumber() { return partNumber;  }
    public void setPartNumber(String partNumber) {  this.partNumber = partNumber;   }

    public String getSerialNumber() {   return serialNumber;    }
    public void setSerialNumber(String serialNumber) {  this.serialNumber = serialNumber;   }

    public String getManufacturer() {   return manufacturer;    }
    public void setManufacturer(String manufacturer) {  this.manufacturer = manufacturer;   }

    public String getAircraftNumber() { return aircraftNumber;  }
    public void setAircraftNumber(String aircraftNumber) {  this.aircraftNumber = aircraftNumber;   }

    public String getComments() {   return comments;    }
    public void setComments(String comments) {  this.comments = comments;   }
}
