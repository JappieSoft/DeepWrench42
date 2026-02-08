package nl.novi.deepwrench42.dtos.aircraft;

import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;

public class AircraftResponseDTO {
    private Long id;
    private String shipNumber;
    private String registration;
    private AircraftTypeResponseDTO aircraftType;
    private EngineTypeResponseDTO engineType;

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

    public String getShipNumber() { return shipNumber;  }
    public void setShipNumber(String shipNumber) {  this.shipNumber = shipNumber;   }

    public String getRegistration() {   return registration;    }
    public void setRegistration(String registration) {  this.registration = registration;   }

    public AircraftTypeResponseDTO getAircraftType() {  return aircraftType;    }
    public void setAircraftType(AircraftTypeResponseDTO aircraftType) { this.aircraftType = aircraftType;   }

    public EngineTypeResponseDTO getEngineType() {  return engineType;  }
    public void setEngineType(EngineTypeResponseDTO engineType) {   this.engineType = engineType;   }
}
