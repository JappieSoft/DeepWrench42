package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.ToolKitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolKitRepository extends JpaRepository<ToolKitEntity, Long> {
    Optional<ToolKitEntity> findByItemId(String itemId);

    boolean existsByStorageLocationId(Long storageLocationId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM aircraft_type_kit_compatibility " +
            "WHERE aircraft_type_id = :aircraftTypeId)", nativeQuery = true)
    boolean existsByAircraftTypeId(@Param("aircraftTypeId") Long aircraftTypeId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM engine_type_kit_compatibility " +
            "WHERE engine_type_id = :engineTypeId)", nativeQuery = true)
    boolean existsByEngineTypeId(@Param("engineTypeId") Long engineTypeId);

    @Query("SELECT CONCAT(s.location, ' / ', s.rack, '-', s.shelf) " +
            "FROM StorageLocationEntity s WHERE s.id = :id")
    String findStorageLocationString(@Param("id") Long id);
}
