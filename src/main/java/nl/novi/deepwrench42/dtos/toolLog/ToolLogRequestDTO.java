package nl.novi.deepwrench42.dtos.toolLog;

import jakarta.validation.constraints.*;
import nl.novi.deepwrench42.entities.*;

import java.time.LocalDateTime;

public class ToolLogRequestDTO {

    @NotNull
    private ToolLogActionType actionType;

    private LocalDateTime timeStamp;

    @NotNull
    private UserEntity actionBy;

    @NotNull
    private Long equipmentId;

    private ToolEntity tool;

    private ToolKitEntity toolKit;

    @NotNull
    private AircraftEntity aircraft;

    private String comments;

    // Getters en Setters
    public ToolLogActionType getActionType() {  return actionType;  }
    public void setActionType(ToolLogActionType actionType) {   this.actionType = actionType;   }

    public LocalDateTime getTimeStamp() {   return timeStamp;   }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }

    public UserEntity getActionBy() {   return actionBy;    }
    public void setActionBy(UserEntity actionBy) {  this.actionBy = actionBy;   }

    public Long getEquipmentId() {  return equipmentId; }
    public void setEquipmentId(Long equipmentId) {  this.equipmentId = equipmentId; }

    public ToolEntity getTool() {   return tool;    }
    public void setTool(ToolEntity tool) {  this.tool = tool;   }

    public ToolKitEntity getToolKit() { return toolKit; }
    public void setToolKit(ToolKitEntity toolKit) { this.toolKit = toolKit; }

    public AircraftEntity getAircraft() {   return aircraft;    }
    public void setAircraft(AircraftEntity aircraft) {  this.aircraft = aircraft;   }

    public String getComments() {   return comments;    }
    public void setComments(String comments) {  this.comments = comments;   }
}
