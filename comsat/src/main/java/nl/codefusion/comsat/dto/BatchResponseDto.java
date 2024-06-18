package nl.codefusion.comsat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BatchResponseDto {
    private UUID id;
    private String name;
    private String state;
    private Date lastModified;
    private Date createdAt;
    private List<BatchResponseContactDto> contacts;
    private List<TemplateDto> templates;

}
