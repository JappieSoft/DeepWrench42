package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class EquipmentEntity extends BaseEntity{

    @Column(name = "equipment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType = EquipmentType.TOOL;

    @Column(name = "item_id", nullable = false)
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
    private Boolean hasInspection;

    @Column(name = "comments")
    private String comments;

    // Getters en Setters

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

    public Boolean getHasInspection() {  return hasInspection;   }
    public void setHasInspection(Boolean hasInspection) {   this.hasInspection = hasInspection; }

    public String getComments() {   return comments;    }
    public void setComments(String comments) {  this.comments = comments;   }
}
