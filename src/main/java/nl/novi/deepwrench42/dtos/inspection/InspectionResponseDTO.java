package nl.novi.deepwrench42.dtos.inspection;

import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.entities.InspectionType;

import java.time.LocalDateTime;

public class InspectionResponseDTO {
    private Long id;
    private LocalDateTime inspectionDate;
    private String inspectionType;
    private LocalDateTime nextDueDate;
    private Integer inspectionInterval;
    private ToolResponseDTO tool;

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

    public LocalDateTime getInspectionDate() {  return inspectionDate;  }
    public void setInspectionDate(LocalDateTime inspectionDate) {   this.inspectionDate = inspectionDate;   }

    public String getInspectionType() { return inspectionType;  }
    public void setInspectionType(String inspectionType) {  this.inspectionType = inspectionType;   }

    public LocalDateTime getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(LocalDateTime nextDueDate) { this.nextDueDate = nextDueDate; }

    public int getInspectionInterval() {    return inspectionInterval;  }
    public void setInspectionInterval(int inspectionInterval) { this.inspectionInterval = inspectionInterval;   }

    public ToolResponseDTO getTool() {  return tool;    }
    public void setTool(ToolResponseDTO tool) { this.tool = tool;   }
}