package nl.codefusion.comsat.repository;

import nl.codefusion.comsat.models.BatchModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository

public interface BatchRepository extends JpaRepository<BatchModel, UUID> {
}
