package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inspections")
public class InspectionEntity extends BaseEntity {

    @Column(name = "inspection_date")
    private LocalDateTime inspectionDate;

    @Column(name = "inspection_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private InspectionType inspectionType = InspectionType.UNKNOWN;

    @Column(name = "inspection_passed")
    private Boolean inspectionPassed;

    @Column(name = "comments")
    private String comments;

    @Column(name = "next_due_date")
    private LocalDateTime nextDueDate;

    @Column(name = "inspection_interval")
    private Integer inspectionInterval;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id", unique = true)
    private ToolEntity tool;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_kit_id", unique = true)
    private ToolKitEntity toolKit;

    // Getters en Setters
    public LocalDateTime getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDateTime inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public InspectionType getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(InspectionType inspectionType) {
        this.inspectionType = inspectionType;
    }

    public Boolean getInspectionPassed() {
        return inspectionPassed;
    }

    public void setInspectionPassed(Boolean inspectionPassed) {
        this.inspectionPassed = inspectionPassed;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDateTime nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public Integer getInspectionInterval() {
        return inspectionInterval;
    }

    public void setInspectionInterval(Integer inspectionInterval) {
        this.inspectionInterval = inspectionInterval;
    }

    public ToolEntity getTool() {
        return tool;
    }

    public void setTool(ToolEntity tool) {
        this.tool = tool;
    }

    public ToolKitEntity getToolKit() {
        return toolKit;
    }

    public void setToolKit(ToolKitEntity toolKit) {
        this.toolKit = toolKit;
    }
}
