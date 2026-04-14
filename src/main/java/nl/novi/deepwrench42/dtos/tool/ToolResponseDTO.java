package nl.novi.deepwrench42.dtos.tool;

import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentResponseDTO;
import nl.novi.deepwrench42.dtos.inspection.InspectionResponseDTO;

import java.util.Set;

public class ToolResponseDTO extends EquipmentResponseDTO {
    private String toolType;
    private Integer ataCode;
    private String partNumber;
    private String serialNumber;
    private String manufacturer;
    private Set<AircraftTypeResponseDTO> applicableAircraftTypes;
    private Set<EngineTypeResponseDTO> applicableEngineTypes;
    private Boolean isCalibrated;
    private InspectionResponseDTO inspection;
    private Long toolKit;
    private String toolKitItemId;

    // Getters en Setters
    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
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

    public Set<AircraftTypeResponseDTO> getApplicableAircraftTypes() {
        return applicableAircraftTypes;
    }

    public void setApplicableAircraftTypes(Set<AircraftTypeResponseDTO> applicableAircraftTypes) {
        this.applicableAircraftTypes = applicableAircraftTypes;
    }

    public Set<EngineTypeResponseDTO> getApplicableEngineTypes() {
        return applicableEngineTypes;
    }

    public void setApplicableEngineTypes(Set<EngineTypeResponseDTO> applicableEngineTypes) {
        this.applicableEngineTypes = applicableEngineTypes;
    }

    public Boolean getIsCalibrated() {
        return isCalibrated;
    }

    public void setIsCalibrated(Boolean isCalibrated) {
        this.isCalibrated = isCalibrated;
    }

    public InspectionResponseDTO getInspection() {
        return inspection;
    }

    public void setInspection(InspectionResponseDTO inspection) {
        this.inspection = inspection;
    }

    public Long getToolKit() {
        return toolKit;
    }

    public void setToolKit(Long toolKit) {
        this.toolKit = toolKit;
    }

    public String getToolKitItemId() {
        return toolKitItemId;
    }

    public void setToolKitItemId(String toolKitItemId) {
        this.toolKitItemId = toolKitItemId;
    }
}
