package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.InspectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<InspectionEntity, Long> {

    List<InspectionEntity> findByInspectionDateBefore(LocalDateTime now);
    List<InspectionEntity> findByInspectionDateAfter(LocalDateTime now);
    List<InspectionEntity> findOverdueByNextDueDateBefore(LocalDateTime now);
}
