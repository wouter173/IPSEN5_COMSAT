package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.BatchTemplateEntryModel;
import nl.codefusion.comsat.repository.BatchTemplateEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BatchTemplateEntryDao {
    private final BatchTemplateEntryRepository batchTemplateEntryRepository;

    public BatchTemplateEntryModel create(BatchTemplateEntryModel batchTemplateEntryModel) {
        return batchTemplateEntryRepository.save(batchTemplateEntryModel);
    }

    public BatchTemplateEntryModel getById(UUID id) {
        return batchTemplateEntryRepository.findById(id).orElse(null);
    }


    public void deleteAllByBatchId(UUID batchId) {
        List<BatchTemplateEntryModel> batchTemplateEntryModels = batchTemplateEntryRepository.findAll().stream()
                .filter(batchTemplateEntryModel -> batchTemplateEntryModel.getBatch().getId().equals(batchId))
                .toList();

        batchTemplateEntryRepository.deleteAll(batchTemplateEntryModels);
    }
}
