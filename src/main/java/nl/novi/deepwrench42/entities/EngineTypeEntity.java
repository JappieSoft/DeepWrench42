package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "engine_types")
public class EngineTypeEntity extends BaseEntity{

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "main_type")
    private String mainType;

    @Column(name = "sub_type")
    private String subType;
}
