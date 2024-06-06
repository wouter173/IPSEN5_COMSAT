package nl.codefusion.comsat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BatchContactDto {
    private String id;
    private String firstName;
    private String nickname;
    private String platform;
    private String audience;
    private String language;
    private String region;
    private String sex;
}
