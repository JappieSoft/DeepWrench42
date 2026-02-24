package nl.novi.deepwrench42.dtos.toolKit;

import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentResponseDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionResponseDTO;
import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationResponseDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.entities.AircraftTypeEntity;
import nl.novi.deepwrench42.entities.EngineTypeEntity;
import nl.novi.deepwrench42.entities.EquipmentStatus;
import nl.novi.deepwrench42.entities.EquipmentType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ToolKitResponseDTO extends EquipmentResponseDTO {

    private Set<ToolResponseDTO> kitContents;
    private String toolKitType;
    private Integer ataCode;
    private String partNumber;
    private String serialNumber;
    private String manufacturer;
    private Set<AircraftTypeResponseDTO> applicableAircraftTypes;
    private Set<EngineTypeResponseDTO> applicableEngineTypes;
    private Boolean isCalibrated;
    private InspectionResponseDTO inspection;

    // Getters en Setters

    public Set<ToolResponseDTO> getKitContents() { return kitContents; }
    public void setKitContents(Set<ToolResponseDTO> kitContents) { this.kitContents = kitContents; }

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

    public Set<AircraftTypeResponseDTO> getApplicableAircraftTypes() {    return applicableAircraftTypes;  }
    public void setApplicableAircraftTypes(Set<AircraftTypeResponseDTO> applicableAircraftTypes) { this.applicableAircraftTypes = applicableAircraftTypes;   }

    public Set<EngineTypeResponseDTO> getApplicableEngineTypes() {    return applicableEngineTypes;    }
    public void setApplicableEngineTypes(Set<EngineTypeResponseDTO> applicableEngineTypes) {   this.applicableEngineTypes = applicableEngineTypes;   }

    public Boolean getIsCalibrated() {    return isCalibrated;    }
    public void setIsCalibrated(Boolean isCalibrated) { this.isCalibrated = isCalibrated;  }

    public InspectionResponseDTO getInspection() {   return inspection;  }
    public void setInspection(InspectionResponseDTO inspection) {    this.inspection = inspection;   }
}
