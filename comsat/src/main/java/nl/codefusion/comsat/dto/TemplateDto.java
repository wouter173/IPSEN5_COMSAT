package nl.codefusion.comsat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class TemplateDto {
    private UUID id;
    private String platform;
    private String header;
    private String body;
    private String metadata;
    private Date lastModified;
    private Date createdAt;
}
