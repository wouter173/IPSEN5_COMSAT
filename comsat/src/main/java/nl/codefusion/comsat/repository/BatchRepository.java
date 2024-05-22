package nl.codefusion.comsat.repository;

import nl.codefusion.comsat.models.BatchModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BatchRepository extends JpaRepository<BatchModel, UUID> {

}
