package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.StorageLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageLocationRepository extends JpaRepository<StorageLocationEntity, Long> {
}
