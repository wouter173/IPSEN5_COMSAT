package nl.codefusion.comsat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private String username;
    private String role;
    private boolean mfaEnabled;
}
