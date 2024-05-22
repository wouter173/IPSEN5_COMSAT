package nl.codefusion.comsat.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.models.OmitIdBatchModel;
import nl.codefusion.comsat.service.BatchProcesses;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
import java.util.*;

@RestController
public class BatchController {

    @Autowired
    private BatchProcesses batchProcesses;

    @Autowired
    private BatchDao batchDao;

    private Set<BatchModel> sentBatches = new HashSet<>();

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BatchController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @PostMapping("/batch")
    public ResponseEntity<String> handleBatch(@RequestBody OmitIdBatchModel batchModel) {

        BatchModel batch = new BatchModel();
        List<ContactModel> contacts = batchModel.getContacts();
        for (ContactModel contact : contacts) {
            contact.setId(UUID.randomUUID());
        }
        batch.setContacts(contacts);
        batch.setName(batchModel.getName());

        batchProcesses.processBatch(batch);
        batchProcesses.saveBatch(batch);

        return ResponseEntity.ok("Batch processed successfully");
    }

    @GetMapping("/batches")
    public ResponseEntity<List<BatchModel>> getAllBatches() {
        List<BatchModel> batches = batchDao.findAll();
        batches.removeIf(sentBatches::contains);
        sentBatches.addAll(batches);

        try {
            String json = objectMapper.writeValueAsString(batches);
            logger.info("JSON response: {}", json);
        } catch (Exception e) {
            logger.error("Error converting batches to JSON", e);
        }

        return ResponseEntity.ok(batches);
    }

    @GetMapping("/batch/{id}")
    public ResponseEntity<BatchModel> getBatch(UUID id) {
        BatchModel batch = batchDao.findById(id).orElse(null);
                if (batch == null) {
                    return ResponseEntity.notFound().build();
                }
        return ResponseEntity.ok(batch);
    }
}
