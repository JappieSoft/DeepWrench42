package nl.novi.deepwrench42.dtos.tool;

import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import java.util.Set;

public class ToolResponseDTO {
    private Long id;
    private String type;
    private Integer ataCode;
    private String partNumber;
    private String serialNumber;
    private String manufacturer;
    private Set<AircraftTypeResponseDTO> applicableAircraftType;
    private Set<EngineTypeResponseDTO> applicableEngineType;
    private Boolean isCalibrated;
    private ToolKitResponseDTO toolKit;

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

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

    public Set<AircraftTypeResponseDTO> getApplicableAircraftType() {   return applicableAircraftType;  }
    public void setApplicableAircraftType(Set<AircraftTypeResponseDTO> applicableAircraftType) {    this.applicableAircraftType = applicableAircraftType;   }

    public Set<EngineTypeResponseDTO> getApplicableEngineType() {   return applicableEngineType;    }
    public void setApplicableEngineType(Set<EngineTypeResponseDTO> applicableEngineType) {  this.applicableEngineType = applicableEngineType;   }

    public Boolean getIsCalibrated() {    return isCalibrated;    }
    public void setIsCalibrated(Boolean isCalibrated) { this.isCalibrated = isCalibrated;  }

    public ToolKitResponseDTO getToolKit() {    return toolKit; }
    public void setToolKit(ToolKitResponseDTO toolKit) {    this.toolKit = toolKit; }
}
