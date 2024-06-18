package nl.codefusion.comsat.controller;

import nl.codefusion.comsat.config.GlobalExceptionHandler;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.engine.KikEngine;
import nl.codefusion.comsat.service.PermissionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(EngineController.class)
@ContextConfiguration(classes = GlobalExceptionHandler.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KikEngine kikEngine;

    @InjectMocks
    private ContactControllerTest enginesController;

    @MockBean
    private PermissionService permissionService;

    @Test
    public void updateContact_InvalidId() throws Exception {
        when(permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.MANAGE_ENGINES)).thenReturn(true);

        mockMvc.perform(post("/api/v1/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Resource not found"));
    }

    @Test
    void deleteContact_InValidId() throws Exception {
        when(permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.MANAGE_ENGINES)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Resource not found"));
    }

    @Test
    public void test_getEngines_KikEngineThrowsException() throws Exception {
        when(permissionService.hasPermission(any(), any())).thenReturn(true);
        when(kikEngine.getStatus()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/v1/engines"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"platform\":\"kik\",\"status\":\"Unavailable\",\"captchaUrl\":null}]"));
    }


}

