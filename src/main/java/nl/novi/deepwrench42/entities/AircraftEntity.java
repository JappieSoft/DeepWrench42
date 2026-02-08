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

}
