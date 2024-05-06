package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/healthcheck")
@RequiredArgsConstructor
public class healthcheck {
    @RequestMapping
    public String getHealthcheck() {
        return "OK";
    }
}
