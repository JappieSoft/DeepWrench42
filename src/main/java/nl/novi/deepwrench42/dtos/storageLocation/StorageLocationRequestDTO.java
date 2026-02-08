package nl.novi.deepwrench42.dtos.storageLocation;

import jakarta.validation.constraints.*;

public class StorageLocationRequestDTO {
    @NotBlank(message = "Location cannot be blank")
    @Size(min = 3, max = 20, message = "Location must be between 3 & 20 characters long")
    private String location;
    @NotBlank(message = "Rack cannot be blank")
    @Size(min = 1, max = 20, message = "Rack must be between 1 & 20 characters long")
    private String rack;
    @NotBlank(message = "Shelf cannot be blank")
    @Size(min = 1, max = 20, message = "Shelf must be between 1 & 20 characters long")
    private String shelf;
    @Size(max = 255, message = "Comment too long")
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

