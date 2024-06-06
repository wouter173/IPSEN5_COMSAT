package nl.codefusion.comsat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BatchResponseContactDto {

    // Contact fields
    private UUID id;
    private String firstName;
    private String nickname;
    private String platform;
    private String audience;
    private String sex;
    private String language;
    private String region;

    // BatchContactEntry fields
    private String status;
    private Boolean hidden;
}
