package nl.codefusion.comsat.service;

import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service

public class BatchProcesses {

    private final BatchRepository batchRepository;

    @Autowired
    public BatchProcesses(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public void processBatch(BatchModel batch) {
        batch.setState("processed");

        UUID newId = UUID.randomUUID();
        batch.setId(newId);
        batch.setLastModified(new Date());

        if (batch.getCreatedAt() == null) {
            batch.setCreatedAt(new Date());
        }

        for (ContactModel contact : batch.getContacts()) {
            contact.setBatch(batch);
        }
    }

    public BatchModel saveBatch(BatchModel batch) {
        return batchRepository.save(batch);
    }


}
