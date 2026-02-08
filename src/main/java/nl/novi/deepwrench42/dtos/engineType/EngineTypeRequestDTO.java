package nl.novi.deepwrench42.dtos.engineType;

import jakarta.validation.constraints.*;

public class EngineTypeRequestDTO {
    @NotBlank(message = "Manufacturer cannot be blank")
    @Size(min = 3, max = 20, message = "Manufacturer must be between 3 & 20 characters long")
    private String manufacturer;
    @NotBlank(message = "Main type cannot be blank")
    @Size(min = 2, max = 20, message = "Main type must be between 2 & 20 characters long")
    private String mainType;
    @NotBlank(message = "Sub type cannot be blank")
    @Size(min = 2, max = 20, message = "Sub type must be between 2 & 20 characters long")
    private String subType;

    // Getters en Setters
    public String getManufacturer() {   return manufacturer;    }
    public void setManufacturer(String manufacturer) {  this.manufacturer = manufacturer;   }

    public String getMainType() {   return mainType;    }
    public void setMainType(String mainType) {  this.mainType = mainType;   }

    public String getSubType() {    return subType; }
    public void setSubType(String subType) {    this.subType = subType; }
}
