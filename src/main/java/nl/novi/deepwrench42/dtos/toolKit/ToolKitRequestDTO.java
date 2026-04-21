package nl.novi.deepwrench42.dtos.toolKit;

import jakarta.validation.constraints.*;
import nl.novi.deepwrench42.dtos.equipment.EquipmentRequestDTO;

import java.util.Set;

public class ToolKitRequestDTO extends EquipmentRequestDTO {

    @NotBlank(message = "Type cannot be blank")
    @Size(min = 3, max = 20, message = "Type must be between 2 & 20 characters long")
    private String toolKitType;

    @NotNull(message = "ATA code required")
    @Min(value = 0, message = "ATA code must be positive")
    @Digits(integer = 4, fraction = 0, message = "ATA code must be exactly 4 digits")
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
    private Long inspectionId;

    // Getters en Setters
    public String getToolKitType() {
        return toolKitType;
    }

    public void setToolKitType(String toolKitType) {
        this.toolKitType = toolKitType;
    }

    public Integer getAtaCode() {
        return ataCode;
    }

    public void setAtaCode(Integer ataCode) {
        this.ataCode = ataCode;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Set<Long> getApplicableAircraftTypeIds() {
        return applicableAircraftTypeIds;
    }

    public void setApplicableAircraftTypeIds(Set<Long> applicableAircraftTypeIds) {
        this.applicableAircraftTypeIds = applicableAircraftTypeIds;
    }

    public Set<Long> getApplicableEngineTypeIds() {
        return applicableEngineTypeIds;
    }

    public void setApplicableEngineTypeIds(Set<Long> applicableEngineTypeIds) {
        this.applicableEngineTypeIds = applicableEngineTypeIds;
    }

    public Boolean getIsCalibrated() {
        return isCalibrated;
    }

    public void setIsCalibrated(Boolean isCalibrated) {
        this.isCalibrated = isCalibrated;
    }

    public Long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Long inspectionId) {
        this.inspectionId = inspectionId;
    }
}


