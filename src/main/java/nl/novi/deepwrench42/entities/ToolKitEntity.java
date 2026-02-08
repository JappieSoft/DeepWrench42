package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tool_kits")
public class ToolKitEntity  extends EquipmentEntity{

    @OneToMany(mappedBy = "toolKit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ToolEntity> kitContents = new HashSet<>();

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
            name = "aircraft_type_kit_compatibility",
            joinColumns = @JoinColumn(name = "toolkit_id"),
            inverseJoinColumns = @JoinColumn(name = "aircraft_type_id")
    )
    private Set<AircraftTypeEntity> applicableAircraftType  = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "engine_type_kit_compatibility",
            joinColumns = @JoinColumn(name = "toolkit_id"),
            inverseJoinColumns = @JoinColumn(name = "engine_type_id")
    )
    private Set<AircraftTypeEntity> applicableEngineType  = new HashSet<>();

    @Column(name = "calibrated")
    private boolean isCalibrated;

}