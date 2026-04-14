package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmployeeId(String employeeId);

    UserEntity findByKcid(String kcid);

    boolean existsByEmployeeId(String employeeId);

    boolean existsByKcid(String kcid);

    boolean existsBySchipholId(String schipholId);

    boolean existsByEmail(String email);
}
