package nl.codefusion.comsat.controller;

import nl.codefusion.comsat.config.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(HealthCheckController.class)
@ContextConfiguration(classes = GlobalExceptionHandler.class)
public class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHealthCheck_Success() throws Exception {
        mockMvc.perform(get("/api/v1/health")).andExpect(status().isOk());
    }

    @Test
    public void testHealthCheck_Error() throws Exception {
        mockMvc.perform(get("/api/v1/health/error")).andExpect(status().isInternalServerError());
    }

    @Test
    public void testHealthCheck_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/health/not-found")).andExpect(status().isNotFound());
    }
}

