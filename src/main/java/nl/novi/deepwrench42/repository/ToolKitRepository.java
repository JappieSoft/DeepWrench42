package nl.novi.deepwrench42.repository;

import nl.novi.deepwrench42.entities.ToolKitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolKitRepository extends JpaRepository<ToolKitEntity, Long> {
}
