package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final PermissionService permissionService;
    @GetMapping
    public ResponseEntity<String> getUsers() {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.MANAGE_USERS)) {
            return ResponseEntity.ok("Users");
        }
        return ResponseEntity.status(403).body("Forbidden");
    }
}

