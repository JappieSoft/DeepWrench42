package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tools")
public class ToolEntity extends EquipmentEntity{

    @Column(name = "tool_type")
    private String toolType;

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
            name = "aircraft_type_tool_compatibility",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "aircraft_type_id")
    )
    private Set<AircraftTypeEntity> applicableAircraftTypes  = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "engine_type_tool_compatibility",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "engine_type_id")
    )
    private Set<EngineTypeEntity> applicableEngineTypes  = new HashSet<>();

    @Column(name = "is_calibrated")
    private Boolean isCalibrated;

    @OneToOne(mappedBy = "tool", optional = true, cascade = CascadeType.ALL)
    private InspectionEntity inspection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_kit_id")
    private ToolKitEntity toolKit;

    // Getters en Setters
    public String getToolType() {   return toolType;    }
    public void setToolType(String toolType) {  this.toolType = toolType;   }

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

    public Boolean getIsCalibrated() { return isCalibrated;    }
    public void setIsCalibrated(Boolean isCalibrated) { this.isCalibrated = isCalibrated;  }

    public InspectionEntity getInspection() { return inspection; }
    public void setInspection(InspectionEntity inspection) {
        this.inspection = inspection;
        if (inspection != null) {   inspection.setTool(this);   }}

    public ToolKitEntity getToolKit() { return toolKit; }
    public void setToolKit(ToolKitEntity toolKit) { this.toolKit = toolKit; }
}