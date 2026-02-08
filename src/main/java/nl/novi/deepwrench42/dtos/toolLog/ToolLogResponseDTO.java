package nl.novi.deepwrench42.dtos.toolLog;

import nl.novi.deepwrench42.dtos.aircraft.AircraftResponseDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import java.time.LocalDateTime;

public class ToolLogResponseDTO {
    private Long id;
    private LocalDateTime timeStamp;
    private String actionType;
    private UserResponseDTO actionBy;
    private ToolResponseDTO tool;
    private ToolKitResponseDTO toolKit;
    private AircraftResponseDTO aircraft;
    private String comments;

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

    public LocalDateTime getTimeStamp() {   return timeStamp;   }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }

    public String getActionType() { return actionType;  }
    public void setActionType(String actionType) {  this.actionType = actionType;   }

    public UserResponseDTO getActionBy() {  return actionBy;    }
    public void setActionBy(UserResponseDTO actionBy) { this.actionBy = actionBy;   }

    public ToolResponseDTO getTool() {  return tool;    }
    public void setTool(ToolResponseDTO tool) { this.tool = tool;   }

    public ToolKitResponseDTO getToolKit() {    return toolKit; }
    public void setToolKit(ToolKitResponseDTO toolKit) {    this.toolKit = toolKit; }

    public AircraftResponseDTO getAircraft() {  return aircraft;    }
    public void setAircraft(AircraftResponseDTO aircraft) { this.aircraft = aircraft;   }

    public String getComments() {   return comments;    }
    public void setComments(String comments) {  this.comments = comments;   }
}
