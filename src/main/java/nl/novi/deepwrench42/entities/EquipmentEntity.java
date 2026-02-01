package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class EquipmentEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "edited_date")
    private LocalDateTime editDate;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
        editDate = createDate;
    }
    @PreUpdate
    protected void onUpdate() {
        editDate = LocalDateTime.now();
    }

    @Column(name = "itemId")
    private String itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "picture")
    private String picture;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "storage_location_id")
    private StorageLocationEntity storageLocation;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EquipmentStatus status = EquipmentStatus.UNKNOWN;

    @Column(name = "checkedOutBy")
    private String checkedOutBy;

    @Column(name = "checkedOutDate")
    private LocalDateTime checkedOutDate;

    @Column(name = "inspection")
    private boolean hasInspection;

    @OneToOne(mappedBy = "tool", cascade = CascadeType.ALL, optional = true)
    private InspectionEntity inspection;

    @Column(name = "comments")
    private String comments;

}
