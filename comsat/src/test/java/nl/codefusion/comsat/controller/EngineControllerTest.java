package nl.codefusion.comsat.controller;

import nl.codefusion.comsat.config.GlobalExceptionHandler;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.controller.AuthController;
import nl.codefusion.comsat.controller.EngineController;
import nl.codefusion.comsat.dto.EnginesResponseDto;
import nl.codefusion.comsat.engine.KikEngine;
import nl.codefusion.comsat.service.PermissionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.naming.NoPermissionException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(EngineController.class)
@ContextConfiguration(classes = GlobalExceptionHandler.class)
public class EngineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KikEngine kikEngine;

    @InjectMocks
    private EngineController enginesController;

    @MockBean
    private PermissionService permissionService;

    @Test
    public void test_captcha_NoBody() throws Exception {
        when(permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.MANAGE_ENGINES)).thenReturn(true);

        mockMvc.perform(post("/api/v1/engines/solve-captcha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Token is required"));

    }

    @Test
    public void test_getEngines_KikEngineThrowsException() throws Exception {
        when(permissionService.hasPermission(any(), any())).thenReturn(true);
        when(kikEngine.getStatus()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/v1/engines"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"platform\":\"kik\",\"status\":\"Unavailable\",\"captchaUrl\":null}]"));
    }

    @Test
    public void test_getEngines_UserNotAuthenticated() throws Exception {
        when(permissionService.hasPermission(any(), any())).thenReturn(false);

        mockMvc.perform(get("/api/v1/engines"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_getEngines_UserAuthenticated() throws Exception {
        when(permissionService.hasPermission(any(), any())).thenReturn(true);

        mockMvc.perform(get("/api/v1/engines"))
                .andExpect(status().isOk());
    }


}

