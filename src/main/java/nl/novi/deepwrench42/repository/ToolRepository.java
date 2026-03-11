package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.ToolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<ToolEntity, Long> {
    Optional<ToolEntity> findByItemId(String itemId);
    boolean existsByStorageLocationId(Long storageLocationId);

    @Query("SELECT CONCAT(s.location, ' / ', s.rack, '-', s.shelf) " +
            "FROM StorageLocationEntity s WHERE s.id = :id")
    String findStorageLocationString(@Param("id") Long id);
}
