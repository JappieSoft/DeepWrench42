package nl.novi.deepwrench42.dtos.user;

import jakarta.validation.constraints.*;
import nl.novi.deepwrench42.entities.UserRole;

public class UserRequestDTO {
    @NotNull(message = "Employee ID is required")
    @Size(min = 6, max = 7, message = "Employee ID must be 6 or 7 digits long")
    private String employeeId;

    @NotNull(message = "Schiphol ID is required")
    @Size(min = 6, max = 10, message = "Schiphol ID must be between 6 & 20 characters long")
    private String schipholId;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Role is required")
    private UserRole role;

    // Getters en Setters
    public String getEmployeeId() { return employeeId;  }
    public void setEmployeeId(String employeeId) {  this.employeeId = employeeId;   }

    public String getSchipholId() { return schipholId;  }
    public void setSchipholId(String schipholId) {  this.schipholId = schipholId;   }

    public String getEmail() {  return email;   }
    public void setEmail(String email) {    this.email = email; }

    public String getFirstName() {  return firstName;   }
    public void setFirstName(String firstName) {    this.firstName = firstName; }

    public String getLastName() {   return lastName;    }
    public void setLastName(String lastName) {  this.lastName = lastName;   }

    public UserRole getRole() { return role;    }
    public void setRole(UserRole role) {    this.role = role;   }
}
