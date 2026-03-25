package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tool_log")
public class ToolLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_Stamp", nullable = false)
    private LocalDateTime timeStamp;

    @Column(name = "action_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ToolLogActionType actionType;

    @JoinColumn(name = "action_result", nullable = false)
    @Enumerated(EnumType.STRING)
    private EquipmentStatus actionResult;

    @Column(name = "action_user", nullable = false)
    private String actionBy;

    @JoinColumn(name = "item_number", nullable = false)
    private String itemNumber;

    @JoinColumn(name = "item_type", nullable = false)
    private String itemType;

    @JoinColumn(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "ata_code")
    private Integer ataCode;

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "manufacturer")
    private String manufacturer;

    @JoinColumn(name = "aircraft_number")
    private String aircraftNumber;

    @Column(name = "comments")
    private String comments;

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

    public LocalDateTime getTimeStamp() {   return timeStamp;   }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }

    public ToolLogActionType getActionType() {  return actionType;  }
    public void setActionType(ToolLogActionType actionType) {   this.actionType = actionType;   }

    public EquipmentStatus getActionResult() {  return actionResult;    }
    public void setActionResult(EquipmentStatus actionResult) { this.actionResult = actionResult;   }

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
