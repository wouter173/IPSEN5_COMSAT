package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.dto.OmitIdBatchDto;
import nl.codefusion.comsat.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.*;

@RequestMapping(value = "/api/v1")
@RestController
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    private final BatchDao batchDao;

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
        batchService.processBatch(batch);
        batchService.saveBatch(batch);

        return ResponseEntity.ok("Batch processed successfully");
    }

    @PutMapping("/batch/{id}")
    public ResponseEntity<BatchModel> updateBatchName(@PathVariable UUID id, @RequestBody String newName) {
        try {
            BatchModel updatedBatch = batchService.updateBatchName(id, newName);
            return ResponseEntity.ok(updatedBatch);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
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
