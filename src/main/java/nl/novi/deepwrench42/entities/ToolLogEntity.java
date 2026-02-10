package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tool_log")
public class ToolLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_Stamp")
    private LocalDateTime timeStamp;

    @Column(name = "action_type")
    @Enumerated(EnumType.STRING)
    private ToolLogActionType actionType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity actionBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id")
    private ToolEntity tool;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toolkit_id")
    private ToolKitEntity toolKit;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "aircraft_id")
    private AircraftEntity aircraft;

    @Column(name = "comments")
    private String comments;

    @PrePersist
    protected void onCreate() {
        timeStamp = LocalDateTime.now();
    }

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

    public LocalDateTime getTimeStamp() {   return timeStamp;   }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }

    public ToolLogActionType getActionType() {  return actionType;  }
    public void setActionType(ToolLogActionType actionType) {   this.actionType = actionType;   }

    public UserEntity getActionBy() {   return actionBy;    }
    public void setActionBy(UserEntity actionBy) {  this.actionBy = actionBy;   }

    public ToolEntity getTool() {   return tool;    }
    public void setTool(ToolEntity tool) {  this.tool = tool;   }

    public ToolKitEntity getToolKit() { return toolKit; }
    public void setToolKit(ToolKitEntity toolKit) { this.toolKit = toolKit; }

    public AircraftEntity getAircraft() {   return aircraft;    }
    public void setAircraft(AircraftEntity aircraft) {  this.aircraft = aircraft;   }

    public String getComments() {   return comments;    }
    public void setComments(String comments) {  this.comments = comments;   }
}
