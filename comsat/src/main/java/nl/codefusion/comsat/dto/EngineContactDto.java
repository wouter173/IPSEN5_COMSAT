package nl.codefusion.comsat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class EngineContactDto {
    private String username;
    private String message;
    private UUID batchId;
}
