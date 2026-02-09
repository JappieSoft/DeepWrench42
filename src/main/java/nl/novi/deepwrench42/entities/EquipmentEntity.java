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

    @Column(name = "equipment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType = EquipmentType.TOOL;

    @Column(name = "itemId")
    private String itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "picture")
    private String picture;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "storage_location_id")
    private StorageLocationEntity storageLocation;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EquipmentStatus status = EquipmentStatus.UNKNOWN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checked_out_by_user_id")
    private UserEntity checkedOutBy;

    @Column(name = "checked_out_date")
    private LocalDateTime checkedOutDate;

    @Column(name = "inspection")
    private boolean hasInspection;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inspection_id")
    private InspectionEntity inspection;

    @Column(name = "comments")
    private String comments;

    // Getters en Setters

    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

    public LocalDateTime getCreateDate() {  return createDate;  }
    public void setCreateDate(LocalDateTime createDate) {   this.createDate = createDate;   }

    public LocalDateTime getEditDate() {    return editDate;    }
    public void setEditDate(LocalDateTime editDate) {   this.editDate = editDate;   }

    public EquipmentType getEquipmentType() {   return equipmentType;   }
    public void setEquipmentType(EquipmentType equipmentType) { this.equipmentType = equipmentType; }

    public String getItemId() { return itemId;  }
    public void setItemId(String itemId) {  this.itemId = itemId;   }

    public String getName() {   return name;    }
    public void setName(String name) {  this.name = name;   }

    public String getPicture() {    return picture; }
    public void setPicture(String picture) {    this.picture = picture; }

    public StorageLocationEntity getStorageLocation() { return storageLocation; }
    public void setStorageLocation(StorageLocationEntity storageLocation) { this.storageLocation = storageLocation; }

    public EquipmentStatus getStatus() {    return status;  }
    public void setStatus(EquipmentStatus status) { this.status = status;   }

    public UserEntity getCheckedOutBy() {   return checkedOutBy;    }
    public void setCheckedOutBy(UserEntity checkedOutBy) {  this.checkedOutBy = checkedOutBy;   }

    public LocalDateTime getCheckedOutDate() {  return checkedOutDate;  }
    public void setCheckedOutDate(LocalDateTime checkedOutDate) {   this.checkedOutDate = checkedOutDate;   }

    public boolean isHasInspection() {  return hasInspection;   }
    public void setHasInspection(boolean hasInspection) {   this.hasInspection = hasInspection; }

    public InspectionEntity getInspection() {   return inspection;  }
    public void setInspection(InspectionEntity inspection) {    this.inspection = inspection;   }

    public String getComments() {   return comments;    }
    public void setComments(String comments) {  this.comments = comments;   }
}
