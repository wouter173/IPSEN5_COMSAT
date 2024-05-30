package nl.codefusion.comsat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.codefusion.comsat.config.GlobalExceptionHandler;
import nl.codefusion.comsat.dao.UserDao;
import nl.codefusion.comsat.dto.LoginDto;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.service.JwtService;
import nl.codefusion.comsat.service.TotpService;
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

import java.util.Optional;

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

    @MockBean
    private UserDao userDao;

    @MockBean
    private TotpService totpService;


    @Test
    public void test_Login_Success() throws Exception {
        String username = "test@acme.com";
        String password = "test";

        UserModel userModel = UserModel.builder().username(username).password(password).build();
        LoginDto loginDto = LoginDto.builder().username(username).password(password).build();

        when(userDetailsImplService.loadUserByUsername(username)).thenReturn(userModel);
        when(userDao.findByUsername(username)).thenReturn(Optional.ofNullable(userModel));
        when(jwtService.generateToken(username)).thenReturn("token");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Login_withMfaEnabled_Success() throws Exception {
        String username = "test@acme.com";
        String password = "test";
        String totpSecret = "test";

        String totp = "123456";

        UserModel userModel = UserModel.builder().username(username).password(password).TotpSecret(totpSecret).mfaEnabled(true).build();
        LoginDto loginDto = LoginDto.builder().username(username).password(password).totp(totp).build();

        when(userDetailsImplService.loadUserByUsername(username)).thenReturn(userModel);
        when(userDao.findByUsername(username)).thenReturn(Optional.ofNullable(userModel));
        when(totpService.validateCode(totpSecret, totp)).thenReturn(true);
        when(jwtService.generateToken(username)).thenReturn("token");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Login_withMfaEnabled_InvalidCode() throws Exception {
        String username = "test@acme.com";
        String password = "test";
        String totpSecret = "test";

        String totp = "123456";

        UserModel userModel = UserModel.builder().username(username).password(password).TotpSecret(totpSecret).mfaEnabled(true).build();
        LoginDto loginDto = LoginDto.builder().username(username).password(password).totp(totp).build();

        when(userDetailsImplService.loadUserByUsername(username)).thenReturn(userModel);
        when(userDao.findByUsername(username)).thenReturn(Optional.ofNullable(userModel));
        when(totpService.validateCode(totpSecret, totp)).thenReturn(false);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("{\"error\":\"TOTP_INVALID\"}"));
    }

    @Test
    public void test_Login_withMfaEnabled_MalformedBody() throws Exception {
        String username = "test@acme.com";
        String password = "test";

        UserModel userModel = UserModel.builder().username(username).password(password).mfaEnabled(true).build();
        LoginDto loginDto = LoginDto.builder().username(username).password(password).build();

        when(userDetailsImplService.loadUserByUsername(username)).thenReturn(userModel);
        when(userDao.findByUsername(username)).thenReturn(Optional.ofNullable(userModel));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"totp\":\"Please enter a valid code\"}"));
    }


    @Test
    public void test_Login_MalformedBody() throws Exception {
        String password = "test";
        LoginDto loginDto = LoginDto.builder().password(password).build();

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"username\":\"Please enter your email\"}"));
    }

    @Test
    public void test_Login_NoBody() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"password\":\"Please enter your password\",\"username\":\"Please enter your email\"}"));
    }

    @Test
    public void test_Login_BadCredentials() throws Exception {
        String username = "test@acme.com";
        String password = "test";

        LoginDto loginDto = LoginDto.builder().username(username).password(password).build();

        when(userDetailsImplService.loadUserByUsername(username)).thenThrow(BadCredentialsException.class);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized());
    }
}
