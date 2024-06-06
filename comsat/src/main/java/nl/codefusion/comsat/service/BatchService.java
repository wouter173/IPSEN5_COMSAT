package nl.codefusion.comsat.service;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.dto.BatchDto;
import nl.codefusion.comsat.dto.EngineContactDto;
import nl.codefusion.comsat.engine.KikEngine;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.BatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public void sendBatch(UUID batchId){
        List<BatchContactEntryModel> batchContacts = batchContactEntryDao.findAllByBatchId(batchId);

        List<EngineContactDto> contacts = new ArrayList<>();
        for (BatchContactEntryModel contact : batchContacts ) {
            //TODO Template generation

            var msg = "test test";

            EngineContactDto engineContactDto = EngineContactDto.builder()
                    .batchId(batchId.toString())
                    .message(msg)
                    .username(contact.getContact().getNickname())
                    .build();

            contacts.add(engineContactDto);
        }

        kikEngine.sendTemplateToContacts(contacts);
    }
}
