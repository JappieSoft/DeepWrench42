package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tool_kits")
public class ToolKitEntity extends EquipmentEntity{

    @OneToMany(mappedBy = "toolKit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ToolEntity> kitContents = new HashSet<>();

    @Column(name = "tool_kit_type")
    private String toolKitType;

    @Column(name = "ata_code")
    private Integer ataCode;

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
    private Set<AircraftTypeEntity> applicableAircraftTypes  = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "engine_type_kit_compatibility",
            joinColumns = @JoinColumn(name = "toolkit_id"),
            inverseJoinColumns = @JoinColumn(name = "engine_type_id")
    )
    private Set<EngineTypeEntity> applicableEngineTypes  = new HashSet<>();

    @Column(name = "is_calibrated")
    private Boolean isCalibrated;

    @OneToOne(mappedBy = "toolKit", optional = true)
    private InspectionEntity inspection;

    // Getters en Setters
    public Set<ToolEntity> getKitContents() {   return kitContents; }
    public void setKitContents(Set<ToolEntity> kitContents) {   this.kitContents = kitContents; }

    public String getToolKitType() {   return toolKitType;    }
    public void setToolKitType(String toolKitType) {  this.toolKitType = toolKitType;   }

    public Integer getAtaCode() {   return ataCode; }
    public void setAtaCode(Integer ataCode) {   this.ataCode = ataCode; }

    public String getPartNumber() { return partNumber;  }
    public void setPartNumber(String partNumber) {  this.partNumber = partNumber;   }

    public String getSerialNumber() {   return serialNumber;    }
    public void setSerialNumber(String serialNumber) {  this.serialNumber = serialNumber;   }

    public String getManufacturer() {   return manufacturer;    }
    public void setManufacturer(String manufacturer) {  this.manufacturer = manufacturer;   }

    public Set<AircraftTypeEntity> getApplicableAircraftTypes() {    return applicableAircraftTypes;  }
    public void setApplicableAircraftTypes(Set<AircraftTypeEntity> applicableAircraftTypes) { this.applicableAircraftTypes = applicableAircraftTypes;   }

    public Set<EngineTypeEntity> getApplicableEngineTypes() {    return applicableEngineTypes;    }
    public void setApplicableEngineTypes(Set<EngineTypeEntity> applicableEngineTypes) {   this.applicableEngineTypes = applicableEngineTypes;   }

    public Boolean getIsCalibrated() { return isCalibrated;   }
    public void setIsCalibrated(Boolean isCalibrated) { this.isCalibrated = isCalibrated;  }

    public void setInspection(InspectionEntity inspection) {
        this.inspection = inspection;
        if (inspection != null) {   inspection.setToolKit(this);   }}
    public InspectionEntity getInspection() { return inspection; }
}