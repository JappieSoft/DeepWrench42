package nl.novi.deepwrench42.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "storage_locations")
public class StorageLocationEntity extends BaseEntity{

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "rack")
    private String rack;

    @Column(name = "shelf")
    private String shelf;

}
