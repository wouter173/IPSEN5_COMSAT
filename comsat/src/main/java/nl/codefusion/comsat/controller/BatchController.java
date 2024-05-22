package nl.codefusion.comsat.controller;

import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.models.OmitIdBatchModel;
import nl.codefusion.comsat.service.BatchProcesses;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class BatchController {

    @Autowired
    private BatchProcesses batchProcesses;

    @Autowired
    private BatchDao batchDao;

    @PostMapping("/batch")
    public ResponseEntity<String> handleBatch(@RequestBody OmitIdBatchModel batchModel) {
        BatchModel batch = new BatchModel();
        List<ContactModel> contacts = batchModel.getContacts();
        for (ContactModel contact : contacts) {
            contact.setId(UUID.randomUUID());
            System.out.println(contact.getId());
            System.out.println(contact.getFirstname());
        }
        batch.setContacts(contacts);
        batchProcesses.processBatch(batch);
        batchProcesses.saveBatch(batch);

        return ResponseEntity.ok("Batch processed successfully");
    }

    @GetMapping("/batches")
    public ResponseEntity<List<BatchModel>> getAllBatches() {
        List<BatchModel> batches = batchDao.findAll();
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
