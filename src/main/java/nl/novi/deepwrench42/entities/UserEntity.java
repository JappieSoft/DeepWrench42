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

}