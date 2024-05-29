package nl.codefusion.comsat.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotpSettingsDto {
    @NotNull
    public boolean mfaEnabled;

    @Min(value = 6, message = "Please enter your otp code")
    @NotNull(message = "Please enter your otp code")
    @NotBlank(message = "Please enter your otp code")
    public String totp;
}
