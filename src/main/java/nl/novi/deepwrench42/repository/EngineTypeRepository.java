package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.EngineTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineTypeRepository extends JpaRepository<EngineTypeEntity, Long> {
}