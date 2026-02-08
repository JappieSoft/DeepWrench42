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

    @Column(name = "is_calibrated")
    private boolean isCalibrated;

    // Getters en Setters
    public Set<ToolEntity> getKitContents() {   return kitContents; }
    public void setKitContents(Set<ToolEntity> kitContents) {   this.kitContents = kitContents; }

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

    public Set<AircraftTypeEntity> getApplicableEngineType() {  return applicableEngineType;    }
    public void setApplicableEngineType(Set<AircraftTypeEntity> applicableEngineType) { this.applicableEngineType = applicableEngineType;   }

    public boolean isCalibrated() { return isCalibrated;   }
    public void setIsCalibrated(boolean isCalibrated) { this.isCalibrated = isCalibrated;  }
}