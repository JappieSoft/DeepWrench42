package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "aircraft")
public class AircraftEntity extends BaseEntity {

    @Column(name = "ship_number")
    private String shipNumber;

    @Column(name = "registration")
    private String registration;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_type_id", nullable = false)
    private AircraftTypeEntity aircraftType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_type_id", nullable = false)
    private EngineTypeEntity engineType;

    // Getters en Setters
    public String getShipNumber() { return shipNumber; }
    public void setShipNumber(String shipNumber) {  this.shipNumber = shipNumber;   }

    public String getRegistration() {   return registration;    }
    public void setRegistration(String registration) {  this.registration = registration;   }

    public AircraftTypeEntity getAircraftType() {   return aircraftType;    }
    public void setAircraftType(AircraftTypeEntity aircraftType) {  this.aircraftType = aircraftType;   }

    public EngineTypeEntity getEngineType() {   return engineType;  }
    public void setEngineType(EngineTypeEntity engineType) {    this.engineType = engineType;   }
}
