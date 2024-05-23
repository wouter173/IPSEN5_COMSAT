package nl.codefusion.comsat.controller;

import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.dto.OmitIdBatchDto;
import nl.codefusion.comsat.service.BatchProcesses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping(value = "/api/v1")
@RestController
public class BatchController {

    @Autowired
    private BatchProcesses batchProcesses;

    @Autowired
    private BatchDao batchDao;

    private Set<BatchModel> sentBatches = new HashSet<>();
    @PostMapping("/batch")
    public ResponseEntity<String> handleBatch(@RequestBody OmitIdBatchDto batchModel) {
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
        List<BatchModel> batches = batchDao.getAllBatches();
        batches.removeIf(sentBatches::contains);
        sentBatches.addAll(batches);
        return ResponseEntity.ok(batches);
    }

    @GetMapping("/batch/{id}")
    public ResponseEntity<BatchModel> getBatch(UUID id) {
        BatchModel batch = batchDao.findById(id);
        if (batch == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(batch);
    }
}
