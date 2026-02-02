package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inspections")
public class InspectionEntity extends BaseEntity{

    @Column(name = "inspection_date")
    private LocalDateTime calibrationDate;

    @Column(name = "inspection_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private InspectionType inspectionType = InspectionType.UNKNOWN;

    @Column(name = "next_due_date")
    private LocalDateTime nextDueDate;

    @Column(name = "inspection_interval")
    private int inspectionInterval;

    @OneToOne(mappedBy = "inspection", cascade = CascadeType.ALL)
    private ToolEntity tool;

}
