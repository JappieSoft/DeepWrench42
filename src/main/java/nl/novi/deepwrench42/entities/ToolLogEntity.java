package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tool_log")
public class ToolLogEntity extends BaseEntity {

    @Column(name = "time_Stamp")
    private LocalDateTime timeStamp;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "action_by")
    private String actionBy;

    @Column(name = "tool")
    private String tool;

    @Column(name = "aircraft")
    private String aircraft;

    @Column(name = "comments")
    private String comments;
}