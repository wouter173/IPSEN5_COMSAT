package nl.codefusion.comsat.controller;

import nl.codefusion.comsat.service.BatchProcesses;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class BatchController {

    @PostMapping("/batch")
    public void handleBatch(@RequestBody Batch batch) {
        BatchProcesses batchProcessor = new BatchProcesses();
        batchProcessor.processBatch(batch);
    }
}
