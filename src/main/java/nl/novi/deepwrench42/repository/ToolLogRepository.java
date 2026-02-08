package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.ToolLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolLogRepository extends JpaRepository<ToolLogEntity, Long> {
}
