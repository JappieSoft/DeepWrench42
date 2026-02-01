package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "aircraft_types")
public class AircraftTypeEntity extends BaseEntity{

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "mainType")
    private String mainType;

    @Column(name = "subType")
    private String subType;
}
