package nl.codefusion.comsat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class EntryResponseDto {
    private UUID id;
    private String status;
    private boolean hidden;
    private String message;
    private String batchName;
    private UUID batchId;
    private Date lastModified;
    private Date createdAt;
}
