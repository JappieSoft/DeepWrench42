package nl.novi.deepwrench42.dtos.storageLocation;

public class StorageLocationResponseDTO {
    private Long id;
    private String location;
    private String rack;
    private String shelf;
    private String comments;

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

    public String getLocation() {   return location;    }
    public void setLocation(String location) {  this.location = location;   }

    public String getRack() {   return rack;    }
    public void setRack(String rack) {  this.rack = rack;   }

    public String getShelf() {  return shelf;   }
    public void setShelf(String shelf) {    this.shelf = shelf; }

    public String getComments() {   return comments;    }
    public void setComments(String comments) {  this.comments = comments;   }
}
