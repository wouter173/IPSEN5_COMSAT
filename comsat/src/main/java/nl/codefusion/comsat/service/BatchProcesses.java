package nl.codefusion.comsat.service;

import nl.codefusion.comsat.models.batch;
import org.hibernate.engine.jdbc.batch.spi.Batch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BatchProcesses {

        public void processBatch(Batch batch) {
            batch customBatch = (batch) batch;
            customBatch.setState("processed");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now  = LocalDateTime.now();
            customBatch.setLastModified(dtf.format(now));

        }

}
