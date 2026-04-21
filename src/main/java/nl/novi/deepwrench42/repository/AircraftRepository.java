package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.AircraftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<AircraftEntity, Long> {
    Optional<AircraftEntity> findByShipNumber(String shipNumber);

    boolean existsByAircraftTypeId(Long aircraftTypeId);
}
