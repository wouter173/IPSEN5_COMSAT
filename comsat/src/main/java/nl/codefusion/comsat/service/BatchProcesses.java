package nl.codefusion.comsat.service;

import nl.codefusion.comsat.models.BatchModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BatchProcesses {

        public void processBatch(org.hibernate.engine.jdbc.batch.spi.Batch batch) {
            BatchModel customBatch = (BatchModel) batch;
            customBatch.setState("processed");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now  = LocalDateTime.now();
            customBatch.setLastModified(dtf.format(now));

        }

}
