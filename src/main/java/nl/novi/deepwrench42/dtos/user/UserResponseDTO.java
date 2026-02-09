package nl.novi.deepwrench42.dtos.user;

import nl.novi.deepwrench42.entities.UserRole;

public class UserResponseDTO {
    private Long id;
    private String employeeId;
    private String schipholId;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;

    // Getters en Setters
    public Long getId() {   return id;  }
    public void setId(Long id) {    this.id = id;   }

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

    public UserRole getRole() {   return role;    }
    public void setRole(UserRole role) {  this.role = role;   }
}
