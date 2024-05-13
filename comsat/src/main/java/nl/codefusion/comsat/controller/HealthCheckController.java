package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/health")
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping
    public String getHealth() {
        return "OK";
    }

    @GetMapping("/error")
    public String getError() throws Exception {
        throw new Exception("Error");
    }
}
