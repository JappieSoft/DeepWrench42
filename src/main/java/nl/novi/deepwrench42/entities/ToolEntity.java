package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tools")
public class ToolEntity extends EquipmentEntity{

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
            name = "aircraft_type_tool_compatibility",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "aircraft_type_id")
    )
    private Set<AircraftTypeEntity> applicableAircraftType  = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "engine_type_tool_compatibility",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "engine_type_id")
    )
    private Set<EngineTypeEntity> applicableEngineType  = new HashSet<>();

    @Column(name = "calibrated")
    private boolean isCalibrated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toolkit_id")
    private ToolKitEntity tool_kit;

}