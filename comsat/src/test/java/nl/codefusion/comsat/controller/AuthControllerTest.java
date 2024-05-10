package nl.codefusion.comsat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.codefusion.comsat.config.GlobalExceptionHandler;
import nl.codefusion.comsat.dto.LoginDto;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.service.JwtService;
import nl.codefusion.comsat.service.UserDetailsImplService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(AuthController.class)
@ContextConfiguration(classes = GlobalExceptionHandler.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsImplService userDetailsImplService;


    @Test
    public void test_Login_Success() throws Exception {
        String username = "test";
        String password = "test";

        UserModel userModel = UserModel.builder().username(username).password(password).build();
        LoginDto loginDto = LoginDto.builder().password(username).username(password).build();

        when(userDetailsImplService.loadUserByUsername(username)).thenReturn(userModel);
        when(jwtService.generateToken(username)).thenReturn("token");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Login_MalformedBody() throws Exception {
        String password = "test";
        LoginDto loginDto = LoginDto.builder().password(password).build();

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"username\":\"Username is required\"}"));
    }

    @Test
    public void test_Login_NoBody() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"password\":\"Password is required\",\"username\":\"Username is required\"}"));
    }

    @Test
    public void test_Login_BadCredentials() throws Exception {
        String username = "test";
        String password = "test";

        LoginDto loginDto = LoginDto.builder().password(username).username(password).build();

        when(userDetailsImplService.loadUserByUsername(username)).thenThrow(BadCredentialsException.class);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized());
    }
}
