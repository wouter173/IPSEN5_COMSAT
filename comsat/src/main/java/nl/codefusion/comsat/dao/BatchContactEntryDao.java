package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dto.EngineContactDto;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.BatchContactEntryRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BatchContactEntryDao {
    private final ContactDao contactDao;
    private final Logger logger;

    private final BatchContactEntryRepository batchContactEntryRepository;

    public BatchContactEntryModel create(BatchContactEntryModel contactToBatchModel) {
        return batchContactEntryRepository.save(contactToBatchModel);
    }

    public BatchContactEntryModel findByBatchIdAndContactId(UUID batchId, UUID contactId) {
        return batchContactEntryRepository.findByBatchIdAndContactId(batchId, contactId);
    }

    public BatchContactEntryModel update(UUID id, BatchContactEntryModel contactToBatchModel) {
        return batchContactEntryRepository.findById(id).map(contactToBatch -> {
            contactToBatch.setStatus(contactToBatchModel.getStatus());

            return batchContactEntryRepository.save(contactToBatch);
        }).orElseThrow();
    }

    public void updateBatchStatusByUsername(List<EngineContactDto> engineContactDto) {
        for (EngineContactDto contactDto : engineContactDto) {

            ContactModel contact = contactDao.findByNickname(contactDto.getUsername());
            if (contact == null) {
                logger.warn("Contact with username {} not found", contactDto.getUsername());
                continue;
            }

            for (BatchContactEntryModel model : contact.getBatchContacts()) {
                if (model.getBatch().getId().equals(UUID.fromString(contactDto.getBatchId()))) {

                    model.setStatus(contactDto.getStatus().toUpperCase());
                    batchContactEntryRepository.save(model);
                }
            }
        }
    }

    public List<BatchContactEntryModel> findAllByBatchId(UUID batchId) {
        return this.batchContactEntryRepository.findAllByBatchId(batchId);
    }

    public BatchContactEntryModel delete(UUID id) {
        BatchContactEntryModel contactToBatch = batchContactEntryRepository.findById(id).orElseThrow();
        batchContactEntryRepository.delete(contactToBatch);
        return contactToBatch;
    }

}
