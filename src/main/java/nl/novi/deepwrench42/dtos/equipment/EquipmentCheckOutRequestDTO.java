package nl.novi.deepwrench42.dtos.equipment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EquipmentCheckOutRequestDTO {
    @NotNull(message = "Equipment item Id is required")
    private String equipmentItemId;
    @NotNull(message = "Employee ID is required")
    @Size(min = 6, max = 7, message = "Employee ID must be 6 or 7 digits long")
    private String employeeNumber;
    @NotBlank(message = "Ship Number cannot be blank")
    @Size(min = 3, max = 20, message = "Ship number must be between 3 & 20 characters long")
    private String aircraft;
    private String comments;

    // Getters en Setters
    public String getEquipmentItemId() {
        return equipmentItemId;
    }

    public void setEquipmentItemId(String equipmentItemId) {
        this.equipmentItemId = equipmentItemId;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
