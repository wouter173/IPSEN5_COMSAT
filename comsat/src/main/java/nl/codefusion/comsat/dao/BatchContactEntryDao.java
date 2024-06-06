package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dto.EngineContactDto;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.BatchContactEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BatchContactEntryDao {
    private final ContactDao contactDao;

    private final BatchContactEntryRepository batchContactEntryRepository;

    public BatchContactEntryModel create(BatchContactEntryModel contactToBatchModel) {
        return batchContactEntryRepository.save(contactToBatchModel);
    }

    public void updateBatchStatusByUsername(List<EngineContactDto> engineContactDto) {

        for (EngineContactDto contactDto : engineContactDto) {
            ContactModel contact = contactDao.findByNickname(contactDto.getUsername());

            for (BatchContactEntryModel model : contact.getBatchContacts()) {
                if (model.getBatch().getId().equals(UUID.fromString(contactDto.getBatchId()))) {
                    model.setStatus(contactDto.getStatus());
                    batchContactEntryRepository.save(model);
                }
            }
        }
    }

    public List<BatchContactEntryModel> findAllByBatchId(UUID batchId){
        return this.batchContactEntryRepository.findAllByBatchId(batchId);
    }

}
