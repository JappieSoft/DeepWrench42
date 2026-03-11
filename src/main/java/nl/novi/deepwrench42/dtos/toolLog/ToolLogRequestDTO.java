package nl.novi.deepwrench42.dtos.toolLog;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import nl.novi.deepwrench42.entities.*;

import java.time.LocalDateTime;

public class ToolLogRequestDTO {

    @NotNull
    private LocalDateTime timeStamp;
    @NotNull
    private ToolLogActionType actionType;
    @NotNull
    private EquipmentStatus actionResult;
    @NotNull
    private String actionBy;
    @NotNull
    private String itemNumber;
    @NotNull
    private String itemType;
    private String itemName;
    private Integer ataCode;
    private String partNumber;
    private String serialNumber;
    private String manufacturer;
    private String aircraftNumber;
    private String comments;

    // Getters en Setters
    public LocalDateTime getTimeStamp() {   return timeStamp;   }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }

    public ToolLogActionType getActionType() {  return actionType;  }
    public void setActionType(ToolLogActionType actionType) {   this.actionType = actionType;   }

    public EquipmentStatus getActionResult() {   return actionResult;    }
    public void setActionResult(EquipmentStatus actionResult) {  this.actionResult = actionResult;   }

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
