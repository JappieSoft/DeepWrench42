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

    @PrePersist
    protected void onCreate() {
        timeStamp = LocalDateTime.now();
    }

    @Column(name = "action_type")
    @Enumerated(EnumType.STRING)
    private ToolLogActionType actionType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity actionBy;

    @Column(name = "equipment_id", nullable = false)
    private Long equipmentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_type", nullable = false)
    private EquipmentType equipmentType;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "aircraft_id")
    private AircraftEntity aircraft;

    @Column(name = "comments", length = 255)
    private String comments;

}
