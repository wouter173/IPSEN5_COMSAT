package nl.codefusion.comsat.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    @NotBlank(message = "username is required")
    @Email(message = "invalid username")
    private String username;

    @NotBlank(message = "password is required")
    private String password;
}
