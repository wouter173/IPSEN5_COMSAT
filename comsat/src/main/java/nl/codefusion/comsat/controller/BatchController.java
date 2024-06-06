package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.dto.BatchDto;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.service.BatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequestMapping(value = "/api/v1")
@RestController
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;
    private final BatchDao batchDao;
    private final ContactDao contactDao;

    private final Set<BatchModel> sentBatches = new HashSet<>();

    @PostMapping("/batch")
    public ResponseEntity<String> handleBatch(@Validated @RequestBody BatchDto batchDto) {

        batchService.processBatch(batchDto);

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
