package nl.codefusion.comsat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnginesResponseDto {
    private String platform;
    private String status;
    private String captchaUrl;
}
