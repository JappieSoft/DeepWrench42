package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.AircraftTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftTypeRepository extends JpaRepository<AircraftTypeEntity, Long> {
}
