package nl.codefusion.comsat.repository;

import nl.codefusion.comsat.models.BatchContactEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface BatchContactEntryRepository extends JpaRepository<BatchContactEntryModel, UUID> {
    List<BatchContactEntryModel> findAllByBatchId(UUID batchId);

    BatchContactEntryModel findByBatchIdAndContactId(UUID batchId, UUID contactId);
}
