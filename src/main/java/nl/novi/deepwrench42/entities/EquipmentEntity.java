package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipment")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class EquipmentEntity extends BaseEntity{

    @Column(name = "itemId")
    private String itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "picture")
    private String picture;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "storage_location_id")
    private StorageLocationEntity storageLocation;

    @Column(name = "checkedOutBy")
    private String checkedOutBy;

    @Column(name = "checkedOutDate")
    private LocalDateTime checkedOutDate;

    @Column(name = "comments")
    private String comments;

}
