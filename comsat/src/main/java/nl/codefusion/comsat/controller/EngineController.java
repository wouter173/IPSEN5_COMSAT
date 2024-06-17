package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dto.EngineSolveCaptchaRequestDto;
import nl.codefusion.comsat.dto.EnginesResponseDto;
import nl.codefusion.comsat.engine.KikEngine;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/engines")
public class EngineController {
    private final KikEngine kikEngine;
    private final PermissionService permissionService;

    @GetMapping()
    public ResponseEntity<List<EnginesResponseDto>> getEngines() throws NoPermissionException {
        if (!permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.MANAGE_ENGINES)) {
            throw new NoPermissionException();
        }

        this.kikEngine.getStatus();

        List<EnginesResponseDto> enginesResponseDtos = new ArrayList<>();

        String kikStatus = "Unavailable";
        if (kikEngine.getStatus().getConnected()) kikStatus = "RequiresCaptcha";
        if (kikEngine.getStatus().getAuthenticated()) kikStatus = "Available";

        enginesResponseDtos.add(EnginesResponseDto.builder()
                .platform("kik")
                .status(kikStatus)
                .captchaUrl(kikEngine.getStatus().getCaptchaUrl())
                .build());

        return ResponseEntity.ok(enginesResponseDtos);
    }

    @PostMapping("/solve-captcha")
    public ResponseEntity<String> solveCaptcha(@RequestBody EngineSolveCaptchaRequestDto solveCaptchaRequestDto) throws NoPermissionException {
        if (!permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.MANAGE_ENGINES)) {
            throw new NoPermissionException();
        }

        this.kikEngine.solveCaptcha(solveCaptchaRequestDto.getToken());
        return ResponseEntity.ok("Captcha solved");
    }
}
