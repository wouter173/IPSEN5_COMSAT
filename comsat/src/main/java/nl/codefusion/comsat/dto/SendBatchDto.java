package nl.codefusion.comsat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class SendBatchDto {
    @NotNull
    private UUID id;
}
