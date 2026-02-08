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

    @Column(name = "comments")
    private String comments;

    // Getters en Setters
    public String getLocation() {   return location;    }
    public void setLocation(String location) {  this.location = location;   }

    public String getRack() {   return rack;    }
    public void setRack(String rack) {  this.rack = rack;   }

    public String getShelf() {  return shelf;   }
    public void setShelf(String shelf) {    this.shelf = shelf; }

    public String getComments() {   return comments;    }
    public void setComments(String comments) {  this.comments = comments;   }
}
