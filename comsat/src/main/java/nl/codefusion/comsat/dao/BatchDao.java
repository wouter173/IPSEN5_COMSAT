package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.repository.BatchRepository;
import org.hibernate.engine.jdbc.batch.spi.Batch;
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
}
