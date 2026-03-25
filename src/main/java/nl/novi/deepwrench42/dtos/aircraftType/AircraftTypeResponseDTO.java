package nl.novi.deepwrench42.dtos.aircraftType;

public class AircraftTypeResponseDTO {
    private Long id;
    private String manufacturer;
    private String mainType;
    private String subType;

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

    public String getManufacturer() {   return manufacturer;    }
    public void setManufacturer(String manufacturer) {  this.manufacturer = manufacturer;   }

    public String getMainType() {   return mainType;    }
    public void setMainType(String mainType) {  this.mainType = mainType;   }

    public String getSubType() {    return subType; }
    public void setSubType(String subType) {    this.subType = subType; }
}
