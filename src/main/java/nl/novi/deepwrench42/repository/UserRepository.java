package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmployeeId(String employeeId);
    boolean existsByEmployeeId(String employeeId);
    boolean existsBySchipholId(String schipholId);
    boolean existsByEmail(String email);
}
