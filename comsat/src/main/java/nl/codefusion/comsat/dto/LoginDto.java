package nl.codefusion.comsat.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    @NotBlank(message = "Please enter your email")
    @Email(message = "Please enter your email")
    private String username;

    @NotBlank(message = "Please enter your password")
    private String password;
}
