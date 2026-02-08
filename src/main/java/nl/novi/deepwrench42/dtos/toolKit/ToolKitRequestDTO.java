package nl.novi.deepwrench42.dtos.toolKit;

import jakarta.validation.constraints.*;
import java.util.Set;

public class ToolKitRequestDTO {
    @NotNull(message = "Tool kit must have content")
    @Size(min = 1, message = "At least one tool required")
    private Set<Long> kitContents;

    @NotBlank(message = "Type cannot be blank")
    @Size(min = 3, max = 20, message = "Type must be between 2 & 20 characters long")
    private String type;

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

    private Set<Long> applicableAircraftTypeIds;
    private Set<Long> applicableEngineTypeIds;

    @NotNull(message = "Calibration status required")
    private Boolean isCalibrated;

    // Getters en Setters
    public Set<Long> getKitContents() { return kitContents; }
    public void setKitContents(Set<Long> kitContents) { this.kitContents = kitContents; }

    public String getType() {   return type;    }
    public void setType(String type) {  this.type = type;   }

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
}


