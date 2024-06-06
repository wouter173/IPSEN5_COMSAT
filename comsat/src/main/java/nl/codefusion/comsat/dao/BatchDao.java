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
        return this.batchRepository.findById(id).map(batch -> {
            batch.setName(batchModel.getName());
            batch.setState(batchModel.getState());
            batch.setLastModified(batchModel.getLastModified());
            return this.batchRepository.save(batch);
        }).orElseThrow(() -> new EntityNotFoundException("Batch not found"));
    }
}
