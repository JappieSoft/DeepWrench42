package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inspections")
public class InspectionEntity extends BaseEntity{

    @Column(name = "inspection_date")
    private LocalDateTime inspectionDate;

    @Column(name = "inspection_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private InspectionType inspectionType = InspectionType.UNKNOWN;

    @Column(name = "next_due_date")
    private LocalDateTime nextDueDate;

    @Column(name = "inspection_interval")
    private int inspectionInterval;

    @OneToOne(mappedBy = "inspection")
    private ToolEntity tool;

    // Getters en Setters
    public LocalDateTime getInspectionDate() {  return inspectionDate;  }
    public void setInspectionDate(LocalDateTime inspectionDate) {   this.inspectionDate = inspectionDate;   }

    public InspectionType getInspectionType() { return inspectionType;  }
    public void setInspectionType(InspectionType inspectionType) {  this.inspectionType = inspectionType;   }

    public LocalDateTime getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(LocalDateTime nextDueDate) { this.nextDueDate = nextDueDate; }

    public int getInspectionInterval() {    return inspectionInterval;  }
    public void setInspectionInterval(int inspectionInterval) { this.inspectionInterval = inspectionInterval;   }

    public ToolEntity getTool() {   return tool;    }
    public void setTool(ToolEntity tool) {  this.tool = tool;   }
}
