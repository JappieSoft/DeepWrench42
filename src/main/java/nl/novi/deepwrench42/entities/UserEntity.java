package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "employee_id", nullable = false, unique = true, length = 7)
    private String employeeId;

    @Column(name = "schiphol_id", unique = true, length = 20)
    private String schipholId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

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