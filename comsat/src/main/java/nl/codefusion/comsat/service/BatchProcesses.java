package nl.codefusion.comsat.service;

import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service

public class BatchProcesses {

    private final BatchRepository batchRepository;
    private final ContactService contactService;

    @Autowired
    public BatchProcesses(BatchRepository batchRepository, ContactService contactService) {
        this.batchRepository = batchRepository;
        this.contactService = contactService;
    }

    public void processBatch(BatchModel batch) {
        batch.setState("processed");

        UUID newId = UUID.randomUUID();
        batch.setId(newId);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        batch.setLastModified(dtf.format(now));

        if (batch.getCreatedAt() == null) {
            batch.setCreatedAt(now);
        }

        batch.setName("Test Batch");


        for (ContactModel contact : batch.getContacts()) {
            contactService.saveContact(contact);
        }
    }

    public BatchModel saveBatch(BatchModel batch) {
        return batchRepository.save(batch);
    }


}
