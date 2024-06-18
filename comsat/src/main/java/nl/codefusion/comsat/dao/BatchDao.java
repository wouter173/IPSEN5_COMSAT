package nl.codefusion.comsat.dao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.repository.BatchRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BatchDao {
    private final BatchRepository batchRepository;

    public BatchModel create(BatchModel batchModel) {
        return batchRepository.save(batchModel);
    }

    public List<BatchModel> getAllBatches() {
        return batchRepository.findAll();
    }

    public BatchModel findById(UUID id) {
        return batchRepository.findById(id).orElse(null);
    }

    public BatchModel update(UUID id, BatchModel batchModel) {
        BatchModel batch = this.batchRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Batch not found"));

        batch.setBatchTemplates(batchModel.getBatchTemplates());
        batch.setBatchContacts(batchModel.getBatchContacts());
        batch.setName(batchModel.getName());
        batch.setState(batchModel.getState());
        
        return this.batchRepository.save(batch);

    }
}
