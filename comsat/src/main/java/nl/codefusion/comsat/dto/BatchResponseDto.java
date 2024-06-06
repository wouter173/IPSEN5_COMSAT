package nl.codefusion.comsat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BatchResponseDto {
    private UUID id;
    private String name;
    private String state;
    private String lastModified;
    private String createdAt;
    private List<BatchResponseContactDto> contacts;
}
