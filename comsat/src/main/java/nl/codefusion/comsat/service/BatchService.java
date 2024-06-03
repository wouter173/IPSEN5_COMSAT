package nl.codefusion.comsat.service;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.dto.BatchDto;
import nl.codefusion.comsat.engine.KikEngine;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.BatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchContactEntryDao batchContactEntryDao;
    private final BatchRepository batchRepository;
    private final ContactDao contactDao;
    private final KikEngine kikEngine;

    @Transactional
    public BatchModel processBatch(BatchDto batchDto) {
        BatchModel batch = new BatchModel();

        batch.setName(batchDto.getName());
        batch.setState("NOTSENT");
        batch.setLastModified(new Date());

        if (batch.getCreatedAt() == null) {
            batch.setCreatedAt(new Date());
        }

        BatchModel newBatch = batchRepository.save(batch);

        for (ContactModel contactDto : batchDto.getContacts()) {
            ContactModel contact = contactDao.findByNickname(contactDto.getNickname());
            if (contact == null) {
                contact = contactDao.create(contact);
            }

            BatchContactEntryModel batchContactEntryModel = BatchContactEntryModel.builder()
                    .contact(contact)
                    .batch(newBatch)
                    .status("NOTSENT")
                    .build();

            batchContactEntryDao.create(batchContactEntryModel);
        }

        return newBatch;
    }
}
