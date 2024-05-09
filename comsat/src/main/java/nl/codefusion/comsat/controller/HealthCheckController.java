package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/healthcheck")
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping
    public String getHealthcheck() {
        return "OK";
    }
}
