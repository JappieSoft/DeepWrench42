package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "aircraft")
public class AircraftEntity extends BaseEntity {

    @Column(name = "shipNumber")
    private String shipNumber;

    @Column(name = "registration")
    private String registration;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_type_id", unique = true)
    private AircraftTypeEntity aircraftType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_type_id", unique = true)
    private EngineTypeEntity engineType;

}
