package nl.novi.deepwrench42.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "employee_id", unique = true)
    private String employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(name = "schiphol_id", unique = true)
    private String schipholId;
}