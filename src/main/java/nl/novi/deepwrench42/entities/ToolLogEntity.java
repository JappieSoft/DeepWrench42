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

    @Column(name = "comments", length = 255)
    private String comments;

    @PrePersist
    protected void onCreate() {
        timeStamp = LocalDateTime.now();
    }
}
