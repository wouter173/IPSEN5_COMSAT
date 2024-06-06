package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.BatchContactEntryRepository;
import nl.codefusion.comsat.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchContactEntryDao {
    private final ContactRepository contactRepository;
    private final BatchContactEntryRepository batchContactEntryRepository;

    public BatchContactEntryModel create(BatchContactEntryModel contactToBatchModel) {
        return batchContactEntryRepository.save(contactToBatchModel);
    }

    public BatchContactEntryModel updateBatchStatusByUsername(String nickName, String status) {
        ContactModel contact = contactRepository.findContactByNickname(nickName);

        // TODO: Get Batch id and and don't use getFirst
        BatchContactEntryModel contactToBatch = contact.getBatchContacts().getFirst();
        contactToBatch.setStatus(status);

        return batchContactEntryRepository.save(contactToBatch);
    }
}
