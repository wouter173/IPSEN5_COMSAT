package nl.codefusion.comsat.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class EngineContactDto {
    private String username;
    private String message;
    private String status;
    @JsonAlias("batch_id")
    private String batchId;
}
