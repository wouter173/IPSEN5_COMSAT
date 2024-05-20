package nl.codefusion.comsat.service;

import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Service

public class BatchProcesses {

    private final BatchRepository batchRepository;

    @Autowired
    public BatchProcesses(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

        public void processBatch(BatchModel batch) {
            batch.setState("processed");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now  = LocalDateTime.now();
            batch.setLastModified(dtf.format(now));

            if(batch.getCreatedAt() == null) {
                batch.setCreatedAt(now);
            }

            batch.setName("Test Batch");

        }

        public BatchModel saveBatch(BatchModel batch) {
            return batchRepository.save(batch);
        }


}
