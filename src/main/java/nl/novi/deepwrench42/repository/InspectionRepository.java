package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.InspectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectionRepository extends JpaRepository<InspectionEntity, Long> {
}
