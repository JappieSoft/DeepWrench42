package nl.novi.deepwrench42.dtos.tool;

import jakarta.validation.constraints.*;
import nl.novi.deepwrench42.dtos.equipment.EquipmentRequestDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionResponseDTO;
import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationResponseDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.entities.EquipmentStatus;
import nl.novi.deepwrench42.entities.EquipmentType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ToolRequestDTO extends EquipmentRequestDTO {
    @NotBlank(message = "Type cannot be blank")
    @Size(min = 3, max = 20, message = "Type must be between 2 & 20  characters long")
    private String toolType;

    @NotNull(message = "ATA code required")
    @Size(min = 4, max = 4, message = "ATA code must be entered with 4 numbers")
    private Integer ataCode;

    @NotBlank(message = "Part number cannot be blank")
    @Size(min = 2, max = 20, message = "Part number must be between 2 & 20 characters long")
    private String partNumber;

    @Size(max = 30, message = "Serial number cannot be longer than 30 characters")
    private String serialNumber;

    @NotBlank(message = "Manufacturer cannot be blank")
    @Size(min = 2, max = 50, message = "Manufacturer must be between 2 & 50 characters long")
    private String manufacturer;

    private Set<Long> applicableAircraftTypeIds = new HashSet<>();
    private Set<Long> applicableEngineTypeIds = new HashSet<>();

    @NotNull(message = "Calibration status required")
    private Boolean isCalibrated;

    private Long inspectionId;
    private Long toolKitId;
    private String toolKitItemId;

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

    public Set<Long> getApplicableAircraftTypeIds() {   return applicableAircraftTypeIds;   }
    public void setApplicableAircraftTypeIds(Set<Long> applicableAircraftTypeIds) { this.applicableAircraftTypeIds = applicableAircraftTypeIds; }

    public Set<Long> getApplicableEngineTypeIds() { return applicableEngineTypeIds; }
    public void setApplicableEngineTypeIds(Set<Long> applicableEngineTypeIds) { this.applicableEngineTypeIds = applicableEngineTypeIds; }

    public Boolean getIsCalibrated() {    return isCalibrated;    }
    public void setIsCalibrated(Boolean isCalibrated) { this.isCalibrated = isCalibrated;  }

    public Long getInspectionId() {   return inspectionId;  }
    public void setInspectionId(Long inspectionId) {    this.inspectionId = inspectionId;   }

    public Long getToolKitId() {    return toolKitId;   }
    public void setToolKitId(Long toolKitId) {  this.toolKitId = toolKitId; }

    public String getToolKitItemId() {  return toolKitItemId;   }
    public void setToolKitItemId(String toolKitItemId) {    this.toolKitItemId = toolKitItemId; }
}