package nl.codefusion.comsat.controller;

import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.OmitIdBatchModel;
import nl.codefusion.comsat.service.BatchProcesses;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

    @Autowired
    private BatchProcesses batchProcesses;

    @PostMapping("/batch")
    public ResponseEntity<String> handleBatch(@RequestBody OmitIdBatchModel batchModel) {
        BatchModel batch = new BatchModel();
        batchProcesses.processBatch(batch);
        return ResponseEntity.ok("Batch processed successfully");
    }
}
