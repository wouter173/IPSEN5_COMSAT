package nl.codefusion.comsat.dto;

import lombok.Builder;
import lombok.Data;
import nl.codefusion.comsat.models.ContactModel;

import java.util.List;

@Data
@Builder
public class BatchDto {
    private String name;
    private String state;
    private String lastModified;
    private String createdAt;
    private List<ContactModel> contacts;
}
