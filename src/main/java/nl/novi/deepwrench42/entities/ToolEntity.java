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
    private ToolKitEntity toolKit;

    // Getters en Setters
    public String getType() {   return type;    }
    public void setType(String type) {  this.type = type;   }

    public int getAtaCode() {   return ataCode; }
    public void setAtaCode(int ataCode) {   this.ataCode = ataCode; }

    public String getPartNumber() { return partNumber;  }
    public void setPartNumber(String partNumber) {  this.partNumber = partNumber;   }

    public String getSerialNumber() {   return serialNumber;    }
    public void setSerialNumber(String serialNumber) {  this.serialNumber = serialNumber;   }

    public String getManufacturer() {   return manufacturer;    }
    public void setManufacturer(String manufacturer) {  this.manufacturer = manufacturer;   }

    public Set<AircraftTypeEntity> getApplicableAircraftType() {    return applicableAircraftType;  }
    public void setApplicableAircraftType(Set<AircraftTypeEntity> applicableAircraftType) { this.applicableAircraftType = applicableAircraftType;   }

    public Set<EngineTypeEntity> getApplicableEngineType() {    return applicableEngineType;    }
    public void setApplicableEngineType(Set<EngineTypeEntity> applicableEngineType) {   this.applicableEngineType = applicableEngineType;   }

    public boolean isCalibrated() { return isCalibrated;    }
    public void setCalibrated(boolean calibrated) { isCalibrated = calibrated;  }

    public ToolKitEntity getToolKit() { return toolKit; }
    public void setToolKit(ToolKitEntity toolKit) { this.toolKit = toolKit; }
}