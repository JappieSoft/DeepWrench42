package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tools")
public class ToolEntity extends EquipmentEntity{

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ToolStatus status = ToolStatus.UNKNOWN;

    @Column(name = "type")
    private String type;

    @Column(name = "ata_code")
    private int ataCode;

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "manufacturer")
    private String manufacturer;

    @ManyToMany
    @JoinTable(
            name = "applicable_aircraft_type",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "aircraft_type_id")
    )
    private Set<AircraftTypeEntity> applicableAircraftType  = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "applicable_engine_type",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "engine_type_id")
    )
    private Set<AircraftTypeEntity> applicableEngineType  = new HashSet<>();

    @Column(name = "calibrated")
    private boolean isCalibrated;

    @OneToOne(mappedBy = "tool", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @Column(name = "calibration")
    private CalibrationEntity calibration;

    @ManyToMany(mappedBy = "tools")
    @Column(name = "part_of_kit")
    private Set<ToolkitEntity> partOfKit = new HashSet<>();

}