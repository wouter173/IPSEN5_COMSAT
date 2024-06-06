package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
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

    public BatchContactEntryModel updateBatchStatusByUsername(String nickName, String status) {
        ContactModel contact = contactDao.findByNickname(nickName);


        // TODO: Get Batch id and and don't use getFirst
        BatchContactEntryModel contactToBatch = contact.getBatchContacts().getFirst();
        contactToBatch.setStatus(status);

        return batchContactEntryRepository.save(contactToBatch);
    }


    public List<BatchContactEntryModel> findAllByBatchId(UUID batchId) {

        return batchContactEntryRepository.findAllByBatchId(batchId);
    }

}
