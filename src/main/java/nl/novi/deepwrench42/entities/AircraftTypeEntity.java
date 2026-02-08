package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "aircraft_types")
public class AircraftTypeEntity extends BaseEntity{

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "main_type")
    private String mainType;

    @Column(name = "sub_type")
    private String subType;

    public String getManufacturer() {   return manufacturer;    }
    public void setManufacturer(String manufacturer) {  this.manufacturer = manufacturer;   }

    public String getMainType() {   return mainType;    }
    public void setMainType(String mainType) {  this.mainType = mainType;   }

    public String getSubType() {    return subType; }
    public void setSubType(String subType) {    this.subType = subType; }
}
