package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final PermissionService permissionService;

    @GetMapping//TODO reset the permission
    public ResponseEntity<String> getContacts() {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_TEMPLATE)){
            return ResponseEntity.ok("Send contacts");
        }
        return ResponseEntity.status(403).body("Forbidden");
    }

}
