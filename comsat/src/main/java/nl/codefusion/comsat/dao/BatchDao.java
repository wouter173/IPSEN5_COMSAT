package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.repository.BatchRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchDao {
    private final BatchRepository batchRepository;

    public BatchModel create(BatchModel batchModel) {
        return batchRepository.save(batchModel);
    }
}
