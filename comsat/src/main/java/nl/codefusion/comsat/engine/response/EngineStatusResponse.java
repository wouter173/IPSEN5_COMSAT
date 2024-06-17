package nl.codefusion.comsat.engine.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EngineStatusResponse {
    private Boolean connected;
    private Boolean authenticated;
    @JsonAlias("captcha_url")
    private String captchaUrl;
}
