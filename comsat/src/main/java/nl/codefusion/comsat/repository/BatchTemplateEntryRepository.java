package nl.codefusion.comsat.repository;

import nl.codefusion.comsat.models.BatchTemplateEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BatchTemplateEntryRepository extends JpaRepository<BatchTemplateEntryModel, UUID> {

}
