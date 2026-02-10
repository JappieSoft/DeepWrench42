package nl.novi.deepwrench42.dtos.aircraft;

import jakarta.validation.constraints.*;

public class AircraftRequestDTO {
    @NotBlank(message = "Ship Number cannot be blank")
    @Size(min = 3, max = 20, message = "Ship number must be between 3 & 20 characters long")
    private String shipNumber;
    @NotBlank(message = "Registration cannot be blank")
    @Size(min = 3, max = 20, message = "Registration must be between 3 & 20 characters long")
    private String registration;
    @NotNull
    private Long aircraftTypeId ;
    @NotNull
    private Long engineTypeId;

    // Getters en Setters
    public String getShipNumber() { return shipNumber;  }
    public void setShipNumber(String shipNumber) {  this.shipNumber = shipNumber;   }

    public String getRegistration() {
        return registration;
    }
    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Long getAircraftTypeId() {   return aircraftTypeId;  }
    public void setAircraftTypeId(Long aircraftTypeId) {    this.aircraftTypeId = aircraftTypeId;   }

    public Long getEngineTypeId() { return engineTypeId;    }
    public void setEngineTypeId(Long engineTypeId) {    this.engineTypeId = engineTypeId;   }
}


