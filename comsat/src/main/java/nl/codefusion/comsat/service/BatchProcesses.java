package nl.codefusion.comsat.service;

import nl.codefusion.comsat.models.BatchModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BatchProcesses {

        public void processBatch(BatchModel batch) {
            batch.setState("processed");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now  = LocalDateTime.now();
            batch.setLastModified(dtf.format(now));

        }

}
