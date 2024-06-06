package nl.codefusion.comsat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDto {
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Nickname cannot be blank")
    private String nickname;

    @NotBlank(message = "Platform cannot be blank")
    private String platform;

    private String audience;

    private String sex;

    private String language;

    private String region;

    private String status;
}